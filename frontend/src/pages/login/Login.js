import React, { useContext, useState } from 'react';
import './Login.css';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { userContext } from '../../UserContext';
function Login() {
    const navigate = useNavigate();
    const [user,setUser] = useContext(userContext);
    const [role,setRole] = useState('customer');
    async function handleSubmit(e){
        try{
          e.preventDefault();
  
          const body = {
            username : Name,
            password : password,
          }
          const response = await axios.post('http://localhost:8080/api/auth/signin/'+role,body);
          if(response.status===200){
            console.log(response.data);
            localStorage.setItem('user',JSON.stringify(response.data));
            setUser(response.data);
            navigate('/home');
          }
          else{
            console.log('error')
          }
        }
        catch(e){
          console.log(e)
          alert(e.response.data.message);
        }
    }
    function handleClick(str){
      setRole(str);
      setName('');
      setPassword('');
    }
    const [Name,setName] = useState('');
    const [password,setPassword] = useState('');

  return (
    <div className='container'>
       {role === 'admin'&& <p style={{zIndex : 1}} className='header'>Admin login</p>} 
        {(role === 'customer') && <button className='role-toggle' onClick={()=>handleClick('admin')}>Login as admin</button>}
        {(role === 'admin') && <button className='role-toggle' onClick={()=>handleClick('customer')}>Login as Customer</button>}
        <img src={(role == 'customer')?"https://www.specsavers-profile.com/wp-content/uploads/Online-education-concept.jpg":"https://th.bing.com/th/id/OIP.3clUiqEgJPbC8zXWQqfCrQHaGM?w=575&h=481&rs=1&pid=ImgDetMain"} className='bg-img' alt="" />
        <form className='form'>
            <label htmlFor="">Name</label>
            <input type="text" className='form-input' value={Name} onChange={(e)=>{setName(e.target.value)}} />
            <label htmlFor="">password</label>
            <input type="password" className='form-input' value={password} onChange={(e)=>setPassword(e.target.value)}/>
            <button type='submit' className='login-button' onClick={(e)=>handleSubmit(e)}>login</button>
        </form>
        <Link to='./register' state={{role: role}} className='link-register'>{(role==='admin')?'New admin':'New user'}</Link>
    </div>
  )
}

export default Login