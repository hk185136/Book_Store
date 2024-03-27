import React, { useState } from 'react'
import './CartBook.css';
import axios from 'axios';
function CartBook({cartItem,deleteItem,setTotal}) {
    const [qty,setQty] = useState(cartItem.quantity);
    async function increment(){
         setQty(prev=>prev+1)
         setTotal(prev=>prev+cartItem.book.price)
        try{
            const res = axios.put(`http://localhost:8080/api/user/cart/${cartItem.id}/increase`,cartItem)
        }
        catch(e){
            alert(e.message);
        }
       
    }
    async function decrement(){
        if(qty === 1){
            deleteItem(cartItem.id);
        }
        setQty(prev=>prev-1)  
        setTotal(prev=>prev-cartItem.book.price)
        try{
            const res = axios.put(`http://localhost:8080/api/user/cart/${cartItem.id}/decrease`,cartItem)
        }
        catch(e){
            alert(e.message);
        }
    }
  return (
    <div className='book-card'>
        <div className='book-img-container'>
            <img src={cartItem.book.url} alt="No image" className='book-img'/>
        </div>
        <div className='book-details'>
            <p className='book-name'>{cartItem.book.title}</p>
            <p className='author-name'>By author : {cartItem.book.author}</p>
            <p className='price'>&#8377;{cartItem.book.price}</p>
            <div className='quantity'>
            <p>Qty : </p>
            {(qty>0) &&  <button className='qty-controller' onClick={()=>{
                if(qty>0)
                    decrement();
                }}>
                    -</button>}
           
            {qty}
            {qty<cartItem.book.availableQuantity && <button className='qty-controller' onClick={()=>{
                if(qty<cartItem.book.availableQuantity){
                    increment();
                }
                
                }}>+</button>}
            </div>
            
            <p className='subtotal'>Sub total : <span style={{color : 'green'}}>&#8377;{cartItem.book.price*qty}</span></p>
            <button onClick={()=>deleteItem(cartItem.id)}>Remove from cart</button>
        </div>
        
    </div>
  )
}

export default CartBook