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
            const res = await axios.get('http://localhost:8080/api/user/notification/getNotfications/'+user.name);
            setMessages(res.data);
        }
        getMessages();
    },[])
    function handleDelete(id){
        axios.delete('http://localhost:8080/api/user/notification/deleteNotification/'+id);
        setMessages(prev=>{
            return prev.filter((msg)=>{return msg.id!=id});
        })
    }
  return (
    <div className='notifications'>
        {messages.length>0 && 
            <table>
                {messages.map((notification)=><tr><Notification notification = {notification} handleDelete = {handleDelete}/></tr>)}              
            </table>
            
        }
    </div>
  )
}

export default Notifications