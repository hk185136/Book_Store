import React, { useContext } from 'react';
import { userContext } from './UserContext';
import { Navigate, Outlet } from 'react-router';
function PrivateRoute() {
    const [user,setUser] = useContext(userContext);
    const localUser = JSON.parse(localStorage.getItem('user'));
  return (
    <>
     {(user.token || (localUser && localUser.token))?<Outlet/>:<Navigate to={'/'}/>}
    </>
   
  )
}

export default PrivateRoute