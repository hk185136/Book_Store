import React, { useContext } from 'react';
import { Navigate, Outlet } from 'react-router';
import { useSelector } from 'react-redux';
function PrivateRoute() {
    const user = useSelector(state=>state);
  return (
    <>
     {(user!=null && user.token!='')?<Outlet/>:<Navigate to={'/'}/>}
    </>
   
  )
}

export default PrivateRoute