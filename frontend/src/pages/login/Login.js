import React, {useEffect, useState } from 'react';
import './Login.css';
import {useLocation, useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { useDispatch} from 'react-redux';
import { toast } from 'react-toastify';
import {Button, FormControl,Paper,Stack,TextField} from '@mui/material';
import LoadingComponent from '../../components/Loading/LoadingComponent';

function Login() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const location = useLocation();
    const [Name,setName] = useState('');
    const [role,setRole] = useState('customer');
    const [isValidName,setIsValidName] = useState(true);
    const [isValidPassword,setIsValidPassword] = useState(true);
    const [nameError,setNameError] = useState('');
    const [passwordError,setPasswordError] = useState('');
    const [password,setPassword] = useState('');
    const [isLoading,setIsLoading] = useState(false);
   
    const paperStyle = {
      padding : '20px',
      textAlign : 'center'
    }
    useEffect(()=>{
      if(location.state && location.state.role){
        setRole(location.state.role);
      }
    },[])
    async function handleSubmit(e){
      if(Name === '' || password === ''){
        if(Name === '' ){
          setIsValidName(false);
          setNameError('Name must be filled')
        }
        if(password === ''){
          setIsValidPassword(false);
          setPasswordError('Password must be filled')
          return;
        }
        return;
      }
     
        try{
          e.preventDefault();
  
          const body = {
            username : Name,
            password : password
          }
          setIsLoading(true);
          const response = await axios.post('http://localhost:8080/api/auth/signin/'+role,body);
          setIsLoading(false);
          if(response.status===200){
            toast.success("Logged in as "+response.data.role,{autoClose:1000});
            dispatch({type : 'login', data : response.data});
            navigate('/home');
          }
        }
        catch(e){
          setName('');
          setPassword('');
          setIsLoading(false);
          setIsValidName(false);
          setIsValidPassword(false);
          setPasswordError('Invalid username or password.')
          toast.error((e?.response?.data?.message) || (e.message));
        }
    }
    function handleClick(str){
      setRole(str);
      setName('');
      setPassword('');
    }

  return (
    <>
    <LoadingComponent isLoading={isLoading}/>
       


      <div className='container'>
        {(role === 'customer') && <Button variant='contained' style={{position : 'absolute',top:'30px',right:'30px'}} className='role-toggle' color='secondary' onClick={()=>handleClick('admin')}>Login as admin</Button>}
        {(role === 'admin') && <Button variant='contained' style={{position : 'absolute',top:'30px',right:'30px'}} className='role-toggle' color='secondary' onClick={()=>handleClick('customer')}>Login as Customer</Button>}

        
    
        <FormControl>
          <Paper elevation={10} style={paperStyle}>
            <h1>Login</h1>
            <Stack spacing={3}>
              <TextField 
                  helperText={nameError} 
                  variant='standard'
                  className='login-input' 
                  error = {(isValidName)?false:true} 
                  required = {true}  
                  label='Name' 
                  value={Name} 
                  onChange={(e)=>{setName(e.target.value)}} 
              />

              <TextField 
                  helperText={passwordError}
                  variant='standard' 
                  type='password' 
                  error = {(isValidPassword)?false:true} 
                  required 
                  label='Password' 
                  value={password} 
                  onChange={(e)=>{setPassword(e.target.value)}} 
              />
              
              <Button 
                  sx={{marginTop:20}} 
                  color='secondary'
                  role='button' 
                  type='submit' 
                  variant='contained'  
                  className='login-button' 
                  onClick={(e)=>handleSubmit(e)}
              >Login
              </Button>
              <Button 
              color='secondary'
              sx={{marginTop:20}} 
              role='button' 
              type='button' 
              variant='outlined'  
              onClick={(e)=>navigate('./register',{state : {role : role}})}
              >{(role==='admin')?'New admin':'New user'}
              </Button>
            </Stack>
          </Paper>
          
            
        </FormControl>
    </div>
    </>

  )
}

export default Login