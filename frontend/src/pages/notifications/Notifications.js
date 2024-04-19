import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import './Notifications.css';
import Notification from '../../components/Notification/Notification';
function Notifications() {
    const [messages,setMessages] = useState([]);
    const user = useSelector(state=>state);
    useEffect(()=>{
        async function getMessages(){
            console.log(user.name)
            const res = await axios.get('http://localhost:8080/api/user/messages/getMessages/'+user.name);
            console.log(res);
            setMessages(res.data);
        }
        getMessages();
    },[])
  return (
    <div className='notifications'>
        {messages.length>0 && 
            messages.map((message)=><Notification description = {message.message}/>)
        }
    </div>
  )
}

export default Notifications