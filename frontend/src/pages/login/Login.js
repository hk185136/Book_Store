import React, { useContext, useState } from 'react';
import './Login.css';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import {Button, FormControl,Paper,Stack,TextField,CircularProgress,Box} from '@mui/material'
function Login() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [role,setRole] = useState('customer');
    const paperStyle = {
      padding : '20px',
      textAlign : 'center'
    }
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
          console.log(response);
          if(response.status===200){
            toast.success("Login successful",{autoClose:1000});
            console.log(response.data);
            dispatch({type : 'login', data : response.data});
            navigate('/home');
          }
          else{
            console.log('error')
          }
        }
        catch(e){
          setName('');
          setPassword('');
          setIsLoading(false);
          setIsValidName(false);
          setIsValidPassword(false);
          setPasswordError('Invalid username or password.')
          console.log(e)
          toast.error((e?.response?.data?.message) || (e.message));
        }
    }
    function handleClick(str){
      setRole(str);
      setName('');
      setPassword('');
    }
    const [Name,setName] = useState('');
    const [isValidName,setIsValidName] = useState(true);
    const [isValidPassword,setIsValidPassword] = useState(true);
    const [nameError,setNameError] = useState('');
    const [passwordError,setPasswordError] = useState('');
    const [password,setPassword] = useState('');
    const [isLoading,setIsLoading] = useState(false);

  return (
    <>
    {isLoading &&  <div className='loadingContainer' style={{zIndex:10,position:'absolute',top:0,left:0,right:0,bottom:0,display:'flex',alignItems:'center',justifyContent : 'center'}}>
          <CircularProgress  size={'50px'}/>
        </div>}
       


        <div className='container'>
       {/* {role === 'admin'&& <p style={{zIndex : 1}} className='header'>Admin login</p>}  */}
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