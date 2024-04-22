import React,{useEffect} from 'react'
import {useState} from 'react';
import {Link,Navigate, useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';
import './Register.css';
import { toast } from 'react-toastify';
import {GetCity,GetCountries,GetState} from 'react-country-state-city'
import {Button, FormControl,Paper,Stack,TextField,Select,MenuItem, InputLabel} from '@mui/material'
import { urls } from '../../api';

function Register() {
    const navigate = useNavigate();
    const location = useLocation();
    const role = location.state.role;
    const [Name,setName] = useState('');
    const [password,setPassword] = useState('');
    const [isValidName,setIsValidName] = useState(true);
    const [nameError,setNameError] = useState('');
    const [isValidPassword,setIsValidPassword] = useState(true);
    const [confirmPassword,setConfirmPassword] = useState('');
    const [country,setCountry] = useState('');
    const [phno,setPhno] = useState('');
    const [countries,setCountries] = useState([]);
    const [states,setStates] = useState([]);
    const [cities,setCities] = useState([]);
    const [state,setState] = useState('');
    const [countryind, setCountryind] = useState(null);
    const [stateind, setStateind] = useState(null);
    const [cityind, setCityind] = useState(null);
    const [city,setCity] = useState('');
    const [passwordError,setPasswordError] = useState([]);
    const [cpasswordError,setcPasswordError] = useState('');
    const [isValidcPassword,setIsValidcPassword] = useState(true);
    const [phone_code,setPhoneCode] = useState(null);
    const [isValidPhoneNum,setIsValidPhoneNum] = useState(true);
    const [phoneError,setPhoneError] = useState('');
    const paperStyle = {
      padding : '20px 20px',
      textAlign : 'center',
      display:'flex',
      flexDirection : 'row',
      gap : '50px'
    }

    
    useEffect(() => {
      GetCountries().then((result) => {
        setCountries(result);
      });
    }, []);

    function validatePhoneNumber(){
      if(phno.length ===0 && phone_code==null){
        return true;
      }
      if(phno.length!==10 || !/[0-9]{10}/.test(phno)){
        return false;
      }
      return true;
    }
    function validateUserName(){
      if(Name.length<4){
        return false;
      }
      return true;
    }
    function validatePassword(){
      let errorMsg = [];
      if(password.length<8){
        errorMsg.push('Password must have 8 or more characters');
      }
      if(/\s/.test(password)){
        errorMsg.push('Password cannot contain spaces');
      }
      if(!/[a-zA-Z]/.test(password)){
        errorMsg.push('Password must contain atleast one alphabet');
      }
      if(!/[A-Z]/.test(password)){
        errorMsg.push('Password must contain atleast one capital letter');
      }
      if(!/[0-9]/.test(password)){
        errorMsg.push('Password must contain atleast one numeric digit');
      }
      if(!/.*[!@#$%^&*()].*/.test(password)){
        errorMsg.push('Password must contain atleast one speacial character');
      }
      setPasswordError(errorMsg);
      
      if(errorMsg.length!==0){
        setIsValidPassword(false);
        return false;
      };
      return true;
    }
    async function handleSubmit(e){
      setPhoneError('')
      setIsValidName(true);
      setIsValidPhoneNum(true);
      setIsValidPassword(true);
      setIsValidcPassword(true);
      setPasswordError([]);
      setcPasswordError('');
      setNameError('');
      try{
        e.preventDefault();
        let flag=false;
        if(password!==confirmPassword){
          setIsValidcPassword(false);
          setcPasswordError('Password and confirm password do not match');
          flag = true;
        }
        if(!validateUserName()){
          setIsValidName(false);
          setNameError('Invalid user name');
          flag = true;
        }
        if(!validatePassword()){
          flag = true
        }
        if(!validatePhoneNumber()){
          setPhoneError('invalid phone number')
          setIsValidPhoneNum(false);
          flag  = true;
        }
        if(flag){
          throw Error('Invalid credentials');
        }

        const body = {
          username : Name,
          password : password,
          role : role,
          address : country+' ,'+state+' ,'+city,
          pno : '+'+phone_code+' - '+phno
        }
        const response = await axios.post(urls.auth.signup,body);
        if(response.status===200){
          navigate('/',{state : {role : role}});
        }

      }
      catch(e){
        if(e?.response?.data?.message && e.response.data.message === 'Username already taken'){
          setIsValidName(false);
          setNameError('Username already taken');
        }
        toast.error((e?.response?.data?.message) || (e.message));
      }
        
    }
    function handleCountryChange(e){
      const curCountry = countries[e.target.value];
      setCountry(curCountry.name);
      setCountryind(e.target.value)
      GetState(curCountry.id).then(res=>{setStates(res)});
      setPhoneCode(curCountry.phone_code)
      setState('');
      setCity('');
      setStateind(null);
      setCityind(null);
    }
    function handleStateChange(e){
      const curState = states[e.target.value];
      setState(curState.name);
      setStateind(e.target.value);
      GetCity(countries[countryind].id,curState.id).then(res=>{
        setCities(res);
      })
      setCity('');
      setCityind(null);
    }
    function handleCityChange(e){
      const curcity = cities[e.target.value];
      setCityind(e.target.value);
      setCity(curcity.name)
    }

  return (
    <div className='container'>
        <FormControl className='form' >
          <Paper elevation={3} style={{padding : '35px 35px'}}>
            <h1 style={{textAlign:'center'}}>Register</h1>
            <div style={paperStyle}>
            <Stack spacing={3}>
                <TextField required error={!isValidName} helperText = {nameError} label='Name' variant='standard' className='login-input' value={Name} 
                    onChange={(e)=>{
                        setName(e.target.value);
                      }} />
                <TextField type="password" variant='standard' error={!isValidPassword} helperText={
                  <ul>
                    {
                      passwordError.map((error)=><li>{error}</li>)
                    }
                  </ul>
                  
                } required label='Password' className='form-input' value={password} onChange={(e)=>{
                    setPassword(e.target.value);
                  }} />
                  <TextField variant='standard' error={!isValidcPassword} helperText={cpasswordError} type="password" required label='Confirm password' className='form-input' value={confirmPassword} 
                  onChange={(e)=>setConfirmPassword(e.target.value)}/>
                  
                   


                  <Button color='secondary' style={{marginTop:'50px'}} type='submit' variant='contained' className='register-button' onClick={(e)=>handleSubmit(e)}>register</Button>
              </Stack>
              {role === 'customer' && <Stack spacing={3}>
                <h2>Address information</h2>
                      <TextField select label='Country' 
                      value={countryind}
                      className='login-input'
                        onChange={(e)=>handleCountryChange(e)}>
                      {
                            countries.map((country,index)=><MenuItem key={country.id} value={index}>{country.name}</MenuItem>)
                          }
                      </TextField>

                      <TextField select label='State' 
                      value={stateind}
                      className='login-input'
                        onChange={(e)=>handleStateChange(e)}>
                      {
                            states.map((state,index)=><MenuItem key={state.id} value={index}>{state.name}</MenuItem>)
                          }
                      </TextField>
                      <TextField select label='City' 
                      value={cityind}
                      className='login-input'
                        onChange={(e)=>handleCityChange(e)}>
                      {
                            cities.map((city,index)=><MenuItem key={city.id} value={index}>{city.name}</MenuItem>)
                          }
                      </TextField>
                      <div>
                      <TextField value={phone_code} onChange={(e)=>setPhoneCode(e.target.value)}  select>
                        {
                          countries.map((country)=><MenuItem value = {country.phone_code}>{country.name} - {country.phone_code}</MenuItem>)
                        }
                        
                      </TextField>
                        <TextField error={!isValidPhoneNum} helperText={phoneError} label='Phone Number' className='login-input' value={phno} 
                        onChange={(e)=>setPhno(e.target.value)}/>

                      </div>
                      
              </Stack>}
              
            </div>
             
          </Paper>
           
          
          
        </FormControl>
    </div>
  )
}

export default Register