import axios from 'axios';
import React, { useEffect, useState } from 'react'
import {Link} from 'react-router-dom'
import './Users.css'
function Users() {
    const [users,setUsers] = useState([]);
    useEffect(()=>{
        async function getUsers(){
            const res = await axios.get('http://localhost:8080/api/auth/getAllUsers');
            console.log(res)
            if(res.status === 200){
                setUsers(res.data);
            }
        }
        getUsers()
    },[])
  return (
    <div className='userlist'>
        {
            users.map((user)=><div>
 <Link key={user.id} to={'/home/orders'} state={{username : user.username}}>{user.username}</Link>
            </div>
               )
        }
    </div>
  )
}

export default Users