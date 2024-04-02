import React from 'react'
import {useState} from 'react';
import {Link,Navigate, useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';
import './Register.css';

function Register() {
    const navigate = useNavigate();
    const location = useLocation();
    const role = location.state.role;

    async function handleSubmit(e){
      try{
        e.preventDefault();
        if(password!==confirmPassword){
          throw Error("password and confirm password do not match.");
        }
        if(!isValidName || !isValidPassword){
          throw Error("Invalid username or password");
        }

        const body = {
          username : Name,
          password : password,
          role : role,
          address : address,
          pno : phno
        }
        const response = await axios.post('http://localhost:8080/api/auth/signup',body);
        if(response.status===200){
          navigate('/');
        }

      }
      catch(e){
        alert(e.message);
      }
        
    }
    const [Name,setName] = useState('');
    const [password,setPassword] = useState('');
    const [isValidName,setIsValidName] = useState(false);
    const [isValidPassword,setIsValidPassword] = useState(false);
    const [confirmPassword,setConfirmPassword] = useState('');
    const [address,setAddress] = useState('');
    const [phno,setPhno] = useState('')
  return (
    <div className='container'>
         <img src={(role === 'customer')?"https://www.specsavers-profile.com/wp-content/uploads/Online-education-concept.jpg":"https://th.bing.com/th/id/OIP.3clUiqEgJPbC8zXWQqfCrQHaGM?w=575&h=481&rs=1&pid=ImgDetMain"} className='bg-img' alt="" />
        <form className='form'>
            <label htmlFor="">Name</label>
            <input type="text" className='form-input' value={Name} 
                  onChange={(e)=>{
                      setName(e.target.value);
                      if(!isValidName && e.target.value.length>=4){
                          setIsValidName(true);
                      }
                      if(isValidName && e.target.value.length<4){
                        setIsValidName(false);
                      }
                    }} />
            <div className='status'>{(isValidName)?(<p className='tick'>&#10003;</p>):(<p>* Username must have 4 or more letters</p>)}</div>
            <label htmlFor="">password</label>
            <input type="password" className='form-input' value={password} onChange={(e)=>{
                      setPassword(e.target.value);
                      if(!isValidPassword && e.target.value.length>=8){
                          setIsValidPassword(true);
                      }
                      if(isValidPassword && e.target.value.length<8){
                        setIsValidPassword(false);
                      }
                    }} />
            <div className='status'>{(isValidPassword)?(<p className='tick'>&#10003;</p>):(<p>* Password must have 8 or more letters</p>)}</div>
            <label htmlFor="">confirm password</label>
            <input type="password" className='form-input' value={confirmPassword} onChange={(e)=>setConfirmPassword(e.target.value)}/>
            {(role === 'customer') && <>
            <label htmlFor="">Address</label>
           <textarea name="" id="" cols="30" rows="10" value={address} onChange={(e)=>setAddress(e.target.value)}></textarea>
           <label htmlFor="">phno</label>
           <input type="text" value={phno} onChange={(e)=>setPhno(e.target.value)}/>
            </>}
          
            <button type='submit' className='login-button' onClick={(e)=>handleSubmit(e)}>register</button>
        </form>
    </div>
  )
}

export default Register