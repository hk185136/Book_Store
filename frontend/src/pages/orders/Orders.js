import React, { useEffect, useState } from 'react'
import './Orders.css';
import Order from '../../components/order/Order';
import axios from 'axios';
import { useLocation } from 'react-router';
import { pending } from '../../OrderStatus';
import { confirmed } from '../../OrderStatus';
import { cancelled } from '../../OrderStatus';
import { delivered } from '../../OrderStatus';
import { toast } from 'react-toastify';
import LoadingComponent from '../../components/Loading/LoadingComponent';
function Orders({username}) {
  const [orders,setOrders] = useState([]);
  const [filteredOrders,setFilteredOrders] = useState([]);
  const [filterStatus,setFilterStatus] = useState('All');
  const [isLoading,setIsLoading] = useState(true);
  const location  = useLocation();
  const username1 = location.state?.username;
  useEffect(()=>{async function get(){
    try{
      const res = await axios.put('http://localhost:8080/api/item/getOrders',{username : username||username1})
      setOrders(res.data);
      console.log(res.data);
      setFilteredOrders(res.data);
      setIsLoading(false);
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  get()
},[username])
function filterOrders(e){
  setIsLoading(true);
  const status = e.target.value;
  setFilterStatus(status);
  if(status === 'All'){
    setFilteredOrders([...orders]);
  }
  else{
    const newFilteredOrders = orders.filter((order)=>order.status === status);
    setFilteredOrders(newFilteredOrders);
  }
  setIsLoading(false);
}
function resetFilters(){
  setIsLoading(true);
  setFilterStatus('*');
  setFilteredOrders([...orders])
  setIsLoading(false);
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
    toast.error((e?.response?.data?.message) || (e.message));
  }
}
  return (
    <div className='orders'>
       <LoadingComponent isLoading={isLoading}/>
      <div className='orders-filter'>
      <select className='order-filters' value={filterStatus} onChange={(e)=>filterOrders(e)}>
        <option value='All'>All</option>
        <option value={pending}>Pending</option>
        <option value={confirmed}>Confirmed</option>
        <option value={cancelled}>Cancelled</option>
        <option value={delivered}>Delivered</option>
      </select>
      <button className='reset-filters' onClick={resetFilters}>Reset filter</button>
      </div>
      
      <div className='orders-grid'>
       
      <>{filteredOrders.length===0 && isLoading===false && <>{<img className='no-orders' src='/emptyOrder.jpg'></img>} </>}</>
        {
          filteredOrders.map((order)=><Order key={order.id} order = {order} removeOrder = {removeOrder}/>)
        }
      </div>
    </div>
   
  )
}

export default Orders