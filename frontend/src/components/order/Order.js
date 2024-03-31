import React, { useState } from 'react'
import './Order.css'
import { cancelled } from '../../OrderStatus';
import axios from 'axios';
import { pending } from '../../OrderStatus';
import { delivered } from '../../OrderStatus';
import { confirmed } from '../../OrderStatus';
function Order({order,removeOrder}) {
  const user =JSON.parse(localStorage.getItem('user'));
  const [status,setStatus] = useState(order.status)
  const total = order.quantity*order.book.price;
  async function updateStatus(newStatus){
    try{
      const res = await axios.put(`http://localhost:8080/api/item/updateStatus/${newStatus}`,order);
      if(res.status === 200){
        const res = await axios.get(`http://localhost:8080/api/user/books/${order.book.id}`);
        if(newStatus == cancelled){
          const book = order.book;
          book.availableQuantity = res.data.availableQuantity+order.quantity;
          axios.put(`http://localhost:8080/api/admin/books/${book.id}`,book)
        }
        else if(status == cancelled && newStatus!=cancelled){
          const book = order.book;
          book.availableQuantity = res.data.availableQuantity-order.quantity;
          axios.put(`http://localhost:8080/api/admin/books/${book.id}`,book)
        }
        setStatus(newStatus)
      }
    }
    catch(e){
      alert(e.message);
    }
  }
  return (
    <div className='order'>
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
                     {(user.role === 'customer') && <>
                      {status!=cancelled && status!=delivered && <button className='order-card-button' onClick={()=>updateStatus(cancelled)}>Cancel</button>}
                      {(status===cancelled || status === delivered) && <button className='order-card-button' onClick={()=>removeOrder(order.id)}>Remove</button>}
                     </>}
                   
                    </div>
                    
                </div>
                
            
            </div>
            <div className='order-adtl-info'>
                <p>Delivering to   :  {order.user.address}</p>
                {(user.role === 'customer' || order.status=='cancelled' || order.status=='delivered') && <p>Status : {status}</p>}
                {user.role === 'admin' && order.status !=='cancelled' && order.status !== 'delivered' && <select name="" id="" value={status} onChange={(e)=>updateStatus(e.target.value)}>
                  <option value={pending}>{pending}</option>
                  <option value={confirmed}>{confirmed}</option>
                  <option value={cancelled}>{cancelled}</option>
                  <option value={delivered}>{delivered}</option>
                </select> }
            </div>
    </div>
    
  )
}

export default Order