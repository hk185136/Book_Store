import React, { useEffect, useState } from 'react'
import './Orders.css';
import Order from '../../components/order/Order';
import axios from 'axios';
import { useLocation } from 'react-router';
import { pending } from '../../OrderStatus';
import { confirmed } from '../../OrderStatus';
import { cancelled } from '../../OrderStatus';
import { delivered } from '../../OrderStatus';
function Orders({username}) {
  const [orders,setOrders] = useState([]);
  const [filteredOrders,setFilteredOrders] = useState([]);
  const [filterStatus,setFilterStatus] = useState('*');
  const location  = useLocation();
  const username1 = location.state?.username;
  useEffect(()=>{async function get(){
    try{
      const res = await axios.put('http://localhost:8080/api/item/getOrders',{username : username||username1})
      setOrders(res.data);
      setFilteredOrders(res.data);
    }
    catch(e){
      alert(e.message);
    }
  }
  get()
},[username])
function filterOrders(e){
  const status = e.target.value;
  setFilterStatus(status);
  if(status === '*'){
    setFilteredOrders([...orders]);
  }
  else{
    const newFilteredOrders = orders.filter((order)=>order.status === status);
    setFilteredOrders(newFilteredOrders);
  }
}
function resetFilters(){
  setFilterStatus('*');
  setFilteredOrders([...orders])
}
async function removeOrder(id){
  try{
    const url = `http://localhost:8080/api/item/${id}`
    const res = await axios.delete(url);
    if(res.status === 200){
      const newOrders = orders.filter((order)=>order.id!==id);
      setOrders(newOrders);
      setFilteredOrders(newOrders);
    }
  }
  catch(e){
    alert(e.message);
  }
}
  return (
    <div className='orders'>
      <div className='orders-filter'>
      <select className='order-filters' value={filterStatus} onChange={(e)=>filterOrders(e)}>
        <option value='*'>*</option>
        <option value={pending}>{pending}</option>
        <option value={confirmed}>{confirmed}</option>
        <option value={cancelled}>{cancelled}</option>
        <option value={delivered}>{delivered}</option>
      </select>
      <button className='reset-filters' onClick={resetFilters}>Reset filter</button>
      </div>
      
      <div className='orders-grid'>
      <>{filteredOrders.length===0 && <img className='no-orders' src='/emptyOrder.jpg'></img>}</>
        {
          filteredOrders.map((order)=><Order key={order.id} order = {order} removeOrder = {removeOrder}/>)
        }
      </div>
    </div>
   
  )
}

export default Orders