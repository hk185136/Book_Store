import React, { useState } from 'react';
import './Login.css';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
function Login() {
    const navigate = useNavigate();
    async function handleSubmit(e){
        try{
          e.preventDefault();
  
          const body = {
            username : Name,
            password : password,
          }
          const response = await axios.post('http://localhost:8080/api/auth/signin',body);
          if(response.status===200)
            navigate('/home');
          else
            alert(response.data.message);
      }
      catch(e){
        alert(e.message);
      }
    }
    const [Name,setName] = useState('');
    const [password,setPassword] = useState('');
  return (
    <div className='container'>
        <img src="https://www.specsavers-profile.com/wp-content/uploads/Online-education-concept.jpg" className='bg-img' alt="" />
        <form className='form'>
            <label htmlFor="">Name</label>
            <input type="text" className='form-input' value={Name} onChange={(e)=>{setName(e.target.value)}} />
            <label htmlFor="">password</label>
            <input type="password" className='form-input' value={password} onChange={(e)=>setPassword(e.target.value)}/>
            <button type='submit' className='login-button' onClick={(e)=>handleSubmit(e)}>login</button>
        </form>
        <Link to='./register' className='link-register'>New user</Link>
    </div>
  )
}

export default Login