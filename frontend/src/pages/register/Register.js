import React from 'react'
import {useState} from 'react';
import {Link,Navigate, useNavigate} from 'react-router-dom';
import axios from 'axios';

function Register() {
    const navigate = useNavigate()
    async function handleSubmit(e){
      try{
        e.preventDefault();
        if(password!==confirmPassword){
          throw Error("password and confirm password do not match.");
        }

        const body = {
          username : Name,
          password : password,
          role : type
        }
        const response = await axios.post('http://localhost:8080/signup',body);
        navigate('/');

      }
      catch(e){
        alert(e.data.message);
      }
        
    }
    const [Name,setName] = useState('');
    const [password,setPassword] = useState('');
    const [confirmPassword,setConfirmPassword] = useState('');
    const [type,setType] = useState('customer');
  return (
    <div className='container'>
         <img src="https://www.specsavers-profile.com/wp-content/uploads/Online-education-concept.jpg" className='bg-img' alt="" />
        <form className='form'>
            <label htmlFor="">Name</label>
            <input type="text" className='form-input' value={Name} onChange={(e)=>{setName(e.target.value)}} />
            <label htmlFor="">User type</label>
            <select name="" id="user-type" value={type} onChange={(e)=>setType(e.target.value)}>
                <option value="admin">Admin</option>
                <option value='customer'>Customer</option>
            </select>
            <label htmlFor="">password</label>
            <input type="password" className='form-input' value={password} onChange={(e)=>setPassword(e.target.value)}/>
            <label htmlFor="">confirm password</label>
            <input type="password" className='form-input' value={confirmPassword} onChange={(e)=>setConfirmPassword(e.target.value)}/>
            <button type='submit' className='login-button' onClick={(e)=>handleSubmit(e)}>register</button>
        </form>
    </div>
  )
}

export default Register