import axios from 'axios';
import React, { useEffect, useState } from 'react'
import './Users.css'
import Orders from '../orders/Orders';
import { urls } from '../../api';
function Users() {
    const [users,setUsers] = useState([]);
    const [username,setUsername] = useState('');
    useEffect(()=>{
        async function getUsers(){
            const res = await axios.get(urls.user.getAllUsers);
            if(res.status === 200){
                setUsers(res.data);
                setUsername(res.data[0].username);
            }
        }
        getUsers()
    },[])
    
  return (
    <div className='user-management'> 
        <div className='userlist'>
            {
                users.map((user)=>  <div className='user-tile' 
                                    key={user.id} 
                                    onClick={()=>setUsername(user.username)}>
                                            <p>{user.username}</p>
                                    </div>
                )
            }
        </div>
        <div className='orders-comp'>
            <Orders username = {username}/>
        </div>
    </div>  
  )
}

export default Users