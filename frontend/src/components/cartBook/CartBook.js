import React, { useState } from 'react'
import './CartBook.css'
function CartBook(props) {
    const [qty,setQty] = useState(1);
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
                    setQty(prev=>prev-1);
                }}>
                    -</button>
            {qty}
            <button className='qty-controller' onClick={()=>setQty(prev=>prev+1)}>+</button>
            </div>
            
            <p className='subtotal'>Sub total : <span style={{color : 'green'}}>&#8377;{props.price*qty}</span></p>
        </div>
        
    </div>
  )
}

export default CartBook