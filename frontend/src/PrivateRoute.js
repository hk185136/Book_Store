import React, { useContext } from 'react';
import { userContext } from './UserContext';
import { Navigate, Outlet } from 'react-router';
function PrivateRoute() {
    const [user,setUser] = useContext(userContext);
  return (
    <>
     {/* {(user.token)?<Outlet/>:<Navigate to={'/'}/>} */}
    <Outlet/>
    </>
   
  )
}

export default PrivateRoute