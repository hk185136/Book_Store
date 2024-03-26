import React, { useContext, useEffect, useState } from 'react'

import CartBooks from '../../components/cartBooks/CartBooks';
import './Cart.css';
import { userContext } from '../../UserContext';
import axios from 'axios';
function Cart({cartItems,setCartItems}) {
  const [user,setUser] = useContext(userContext);
  return (
      <div className='cart'>
        
        <CartBooks cartItems = {cartItems} setCartItems = {setCartItems}/>
        <div>
          <h1>Order summary</h1>
          <p>Total items : </p>
          <p>Total cost : </p>
        </div>
      </div>
      
    
  )
}

export default Cart