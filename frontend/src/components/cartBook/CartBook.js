import React, { useState } from 'react'
import './CartBook.css'
function CartBook(props) {
    const [qty,setQty] = useState(1);
    async function increment(){
         setQty(prev=>prev+1)
        //api call
       
    }
    async function decrement(){
        setQty(prev=>prev-1)  
        //api call
    }
  return (
    <div className='book-card'>
        <div className='book-img-container'>
            <img src={props.image} alt="No image" className='book-img'/>
        </div>
        <div className='book-details'>
            <p className='book-name'>{props.name}</p>
            <p className='author-name'>By author : {props.author}</p>
            <p className='price'>&#8377;{props.price}</p>
            <div className='quantity'>
            <p>Qty : </p>
            <button className='qty-controller' onClick={()=>{
                if(qty>0)
                    decrement();
                }}>
                    -</button>
            {qty}
            <button className='qty-controller' onClick={()=>increment()}>+</button>
            </div>
            
            <p className='subtotal'>Sub total : <span style={{color : 'green'}}>&#8377;{props.price*qty}</span></p>
        </div>
        
    </div>
  )
}

export default CartBook