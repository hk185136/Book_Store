import React, { useState } from 'react'

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
            <label htmlFor="">Qty</label>
            <select name="" id="" value={qty} onChange={(e)=>setQty(e.target.value)}>
                <option value={1}>1</option>
                <option value={2}>2</option>
                <option value={3}>3</option>
            </select>
            <p>Sub total : {props.price*qty}</p>
        </div>
        
    </div>
  )
}

export default CartBook