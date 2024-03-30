import React from 'react'
import CartBook from '../cartBook/CartBook'
import './CartBooks.css';
import axios from 'axios';
function CartBooks({cartItems,setCartItems,setTotal}) {
  async function deleteItem(id){
    try{
      const res = await axios.delete('http://localhost:8080/api/item/' + id);
      const newCartItems = cartItems.filter((item)=>item.id!==id);
      setCartItems(newCartItems);
    }
    catch(e){
      alert(e.message);
    }
  }

  return (
    <div className='card-grid'>
      {
        cartItems.map((cartItem) => <CartBook key={cartItem.id} cartItem = {cartItem} deleteItem = {deleteItem} setTotal = {setTotal}/>)
      }
     
    </div>
  )
}

export default CartBooks