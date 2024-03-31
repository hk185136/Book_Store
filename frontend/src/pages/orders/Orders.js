import React, { useEffect, useState } from 'react'
import './Orders.css';
import Order from '../../components/order/Order';
import axios from 'axios';
import { useLocation } from 'react-router';
function Orders({username}) {
  const [orders,setOrders] = useState([]);
  const location  = useLocation();
  const username1 = location.state?.username;
  useEffect(()=>{async function get(){
    try{
      const res = await axios.put('http://localhost:8080/api/item/getOrders',{username : username||username1})
      setOrders(res.data)
    }
    catch(e){
      alert(e.message);
    }
  }
  get()
},[username])
async function removeOrder(id){
  try{
    const url = `http://localhost:8080/api/item/${id}`
    const res = await axios.delete(url);
    if(res.status === 200){
      const newOrders = orders.filter((order)=>order.id!==id);
      setOrders(newOrders);
    }
  }
  catch(e){
    alert(e.message);
  }
}
  return (
    <div className='orders'>
     
    <div className='orders-grid'>
    <>{orders.length===0 && <img className='no-orders' src='/emptyOrder.jpg'></img>}</>
      {
        orders.map((order)=><Order key={order.id} order = {order} removeOrder = {removeOrder}/>)
      }
    </div>
    </div>
   
  )
}

export default Orders