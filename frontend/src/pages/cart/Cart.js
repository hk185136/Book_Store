import React, {useState } from 'react'
import CartBooks from '../../components/cartBooks/CartBooks';
import './Cart.css';
import axios from 'axios';
import Modal from '../../components/modal/Modal';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import LoadingComponent from '../../components/Loading/LoadingComponent';

function Cart({cartItems,setCartItems}) {
  const user = useSelector(state=>state);
  const [address,setAddress] = useState(user.address||'');
  const [total,setTotal] = useState(getTotal());
  const [isOpen,setIsOpen] = useState(false);
  const [isLoading,setIsLoading] = useState(false);

  function getTotal(){
    let total = 0;
    for(let cartItem of cartItems){
      total += (cartItem.book.price*cartItem.quantity)
    }
    return total;
  }
  async function handleBuy(e){
    setIsOpen(false);
    setIsLoading(true);
    e.preventDefault();
    
    for(let cartItem of cartItems){
      try{
        const book_res = await axios.get(`http://localhost:8080/api/user/books/${cartItem.book.id}`)
        const book = book_res.data;
        if(book.availableQuantity===0){
          await axios.delete(`http://localhost:8080/api/item/${cartItem.id}`)
          setCartItems(prev => prev.filter((cartItem1) => cartItem1.id!==cartItem.id));
          continue;
        }
        const qty = (book.availableQuantity<cartItem.quantity)?book.availableQuantity:cartItem.quantity
        const body = {
          book : book,
          user : {
            username : user.name,
            address : address,
            pno: user.pno
          },
          quantity : qty
        }
        const res = await axios.post('http://localhost:8080/api/item/addToOrder',body);
        if(res.status === 200){
          const newBook = book;
          newBook.availableQuantity = newBook.availableQuantity - qty;
          axios.put(`http://localhost:8080/api/admin/books/${book.id}`,newBook)
          const res = await axios.delete(`http://localhost:8080/api/item/${cartItem.id}`);
          setCartItems(prev => prev.filter((cartItem1) => cartItem1.id!==cartItem.id));
        }
      }
      catch(e){
        toast.error((e?.response?.data?.message) || (e.message));
      }
      finally{
        setIsLoading(false);
      }

    }
  }
  return (
    <div className='cart'>
      <LoadingComponent isLoading={isLoading}/>
      {(cartItems.length>0)?<>
        <CartBooks cartItems = {cartItems} setCartItems = {setCartItems} setTotal = {setTotal}/>
        <div className='order1'>
          <h1 className='order-summary'>Order summary</h1>
          <p className='total-items'>Total items : {cartItems.length}</p>
          <p className='total-cost'>Total cost : <span style={{color : 'green'}}>&#8377;{total}</span></p>
          <button className='buy-button' onClick={()=>setIsOpen(true)}>Buy</button>
        </div>
      </>: 
      <img src='/emptyCart.png'></img>}
      {isOpen && <Modal setIsOpen = {setIsOpen}>
        <form className='add-book-form'>
            {(user.address==='') && <p>Your account does not have an address</p>}
            <label htmlFor="">Address</label>
            <textarea name="" id="" cols="30" rows="10" value={address} onChange={(e)=>setAddress(e.target.value)}></textarea>
            
            <button className='buy-button' onClick={(e)=>handleBuy(e)}>Buy</button>
        </form>
      </Modal>}
      
    </div> 
  )
}

export default Cart;