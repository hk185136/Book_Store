import React, { useEffect, useState } from 'react'
import { IoClose } from "react-icons/io5";
import './Order.css'
import { Cancelled,Delivered } from '../../OrderStatus';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import { CircularProgress } from '@mui/material';
import { ProgressBar, Step } from "react-step-progress-bar";
import "react-step-progress-bar/styles.css";
import { GrDeliver } from "react-icons/gr";
import { TbHomeCheck } from "react-icons/tb";
import { LuPackageCheck } from "react-icons/lu";

function Order({order,removeOrder}) {
  console.log(order.status);
  const user = useSelector(state=>state);
  const [status,setStatus] = useState(order.status);
  console.log(status);
  const [isLoading,setIsLoading] = useState(false);
  const statusProgressMapping = {
    Confirmed : 10,
    'On the way' : 50,
    'Delivered' : 100
  }
  useEffect(()=>{
    if(status!=='Cancelled'){
      setStatus(order.status)
    }
  })
  const total = order.quantity*order.book.price;
  function asignStyleToProgress(isAccomplished){
    if(isAccomplished) return {
      backgroundColor : 'green'
    }
    else return {
      backgroundColor: 'rgb(196, 196, 196)'
    }
  }
  async function cancelOrder(){
    try{
      // Update status of item.
      setIsLoading(true);
      const res = await axios.put(`http://localhost:8080/api/item/updateStatus/${Cancelled}`,order);
      setIsLoading(false);
      if(res.status === 200){
        toast.success("Order cancelled")
        setStatus(Cancelled);
        // Get the existing copy of book.
        const res = await axios.get(`http://localhost:8080/api/user/books/${order.book.id}`);
        if(res.status == 200 && res.data){
          const book = order.book;
          book.availableQuantity = res.data.availableQuantity+order.quantity;
          // As order is cancelled, increase the available quantity of the ordered book.
          await axios.put(`http://localhost:8080/api/admin/books/${book.id}`,book)
        }
      }
    }
    catch(e){
      setIsLoading(false);
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  return (
    <div className='order'>

      {(status===Cancelled || status === Delivered) 
        && user.role === 'customer' 
        && <IoClose className='close-icon' onClick={()=>removeOrder(order.id)}/>
      }

      <div className='book-card' style={{boxShadow:'none',borderRadius:0,transition:'none',transform:'none'}}>
        <div className='book-img-container'>
            <img src={order.book.url} alt="No image" className='book-img'/>
        </div>

        <div className='book-details'>
            <p className='book-name'>{order.book.title}</p>
            <p className='author-name'>{order.book.author}</p>
            <p className='price'>&#8377;{order.book.price}</p>

            <div className='quantity'>
              <p>Qty : {order.quantity}</p>
            </div>
            
            <p className='subtotal'>Total : <span style={{color : 'green'}}>&#8377;{total}</span></p>

            <div>
              {(user.role === 'customer') 
              && <>
              {status!=Cancelled && status!=Delivered && 
              <>{
                  isLoading ? <CircularProgress/> : 
                  <button 
                    disabled = {isLoading} 
                    className='order-card-button' 
                    onClick={cancelOrder}
                  >Cancel
                  </button>
                }
              </> }
              {(status===Cancelled || status === Delivered) 
              &&  <button 
                    className='order-card-button' 
                    onClick={()=>removeOrder(order.id)}
                    >Remove
                  </button>
              }
              </>}
            </div>  
        </div>
      </div>
      
      <div className='order-adtl-info'>
          <p>Delivering to   :  {order.user.address}</p>
          <p>Status : {status}</p>
          
      </div>
      {status !== Cancelled && 
      <div style={{padding : '20px 20px'}}>
        <ProgressBar percent = {statusProgressMapping[status]} height = {'5px'}>
          <Step>
            {({ accomplished }) => (
              <div className='status-step' style={asignStyleToProgress(accomplished)}>
                <LuPackageCheck size={30}/>
              </div>
            )}
          </Step>
          <Step>
            {({ accomplished }) => (
              <div className='status-step' style={asignStyleToProgress(accomplished)}>
                <GrDeliver size={30}/>
              </div>
            )}
          </Step>
          <Step>
            {({ accomplished }) => (
              <div className='status-step' style={asignStyleToProgress(accomplished)}>
                <TbHomeCheck size={30}/>
              </div>
            )}
          </Step>
        </ProgressBar>
      </div>
      }
      
              
    </div>
  )
}

export default Order