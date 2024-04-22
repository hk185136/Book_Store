import React, { useEffect, useState } from 'react'
import './Orders.css';
import Order from '../../components/order/Order';
import axios from 'axios';
import { useLocation } from 'react-router';
import { Confirmed,OnTheWay,Delivered,Cancelled } from '../../OrderStatus';
import { toast } from 'react-toastify';
import LoadingComponent from '../../components/Loading/LoadingComponent';

function Orders({username,reload}) {
  const [orders,setOrders] = useState([]);
  const [filteredOrders,setFilteredOrders] = useState([]);
  const [filterStatus,setFilterStatus] = useState('All');
  const [isLoading,setIsLoading] = useState(true);
  const location  = useLocation();
  const username1 = location.state?.username;

  useEffect(()=>{async function get(){
    try{
      setIsLoading(true);
      const res = await axios.put('http://localhost:8080/api/item/getOrders',{username : username||username1})
      setOrders(res.data);
      setFilteredOrders(res.data);
      setIsLoading(false);
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  get()
},[username])

// useEffect(()=>{
//   async function subscribe(){
//   // Subscribe user to the notifications
//     const eventSource = new EventSource("http://localhost:8080/api/user/subscription/enableSubscription/"+username1);
//     eventSource.addEventListener("Order status",async (event)=>{
//       try{
//         console.log("event captured")
//         const res = await axios.put('http://localhost:8080/api/item/getOrders',{username : username||username1})
//         setOrders(res.data);
//         setFilteredOrders(res.data);
//       }
//       catch(e){
//         toast.error((e?.response?.data?.message) || (e.message));
//       }
//     })
//     eventSource.onerror = (event) => {
//       console.log(event.target.readyState)
//       if (event.target.readyState === EventSource.CLOSED) {
//         console.log('eventsource closed (' + event.target.readyState + ')')
//       }
//       eventSource.close();
//     }
//   }
//     subscribe();
  
// },[])
useEffect(()=>{
  async function get(){
    try{
      setIsLoading(true);
      const res = await axios.put('http://localhost:8080/api/item/getOrders',{username : username||username1})
      console.log(res);
      setOrders(res.data);
      setFilteredOrders(res.data);
      setIsLoading(false);
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
    get();
},[reload])

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
          <option value={Confirmed}>Confirmed</option>
          <option value={Cancelled}>Cancelled</option>
          <option value={Delivered}>Delivered</option>
          <option value={OnTheWay}>On the way</option>
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