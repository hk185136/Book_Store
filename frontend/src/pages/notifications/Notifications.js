import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux';
import './Notifications.css';
import Notification from '../../components/Notification/Notification';
import {Button} from '@mui/material'
import { urls } from '../../api';
function Notifications() {
    const [messages,setMessages] = useState([]);
    const user = useSelector(state=>state);
    useEffect(()=>{
        async function getMessages(){
            const res = await axios.get(urls.notification.getNotificationsByUser+user.name);
            setMessages(res.data);
        }
        getMessages();
    },[])
    function handleDelete(id){
        axios.delete(urls.notification.deleteNotification+id);
        setMessages(prev=>{
            return prev.filter((msg)=>{return msg.id!=id});
        })
    }
    function handleClear(){
        setMessages([]);
        axios.delete(urls.notification.deleteNotificationsByUser+user.name);
    }
  return (
    <div className='notifications'>
        <h2 style={{textAlign:'center'}}>Notifications</h2>
        <div className='notifications-table'>
        <Button style={{width : '50px',marginBottom:'25px'}} variant='contained' color='secondary' onClick={handleClear}>Clear</Button>
        
        {messages.length>0 && 
            <table>
                <tr>
                    <th>Time</th>
                    <th>Message</th>
                </tr>
                {messages.map((notification)=><Notification notification = {notification} handleDelete = {handleDelete}/>)}              
            </table>
            
        }
        </div>
        
    </div>
  )
}

export default Notifications