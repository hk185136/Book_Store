import React, { useContext, useEffect, useState } from 'react'

import CartBooks from '../../components/cartBooks/CartBooks';
import './Cart.css';
import { userContext } from '../../UserContext';
import axios from 'axios';
function Cart({cartItems,setCartItems}) {
  const [user,setUser] = useContext(userContext);
  const [total,setTotal] = useState(getTotal());
  console.log(cartItems)
  function getTotal(){
    let total = 0;
    for(let cartItem of cartItems){
      total += (cartItem.book.price*cartItem.quantity)
    }
    return total;
  }
  return (
      <div className='cart'>
        {(cartItems.length>0)?<>
          <CartBooks cartItems = {cartItems} setCartItems = {setCartItems} setTotal = {setTotal}/>
          <div className='order'>
          <h1>Order summary</h1>
          <p>Total items : {cartItems.length}</p>
          <p>Total cost : {total}</p>
        </div>
        </>:
        <img src='/emptyCart.png'></img>}
        
        
      </div>
      
    
  )
}

export default Cart