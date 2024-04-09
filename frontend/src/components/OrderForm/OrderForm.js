import React, { useReducer, useState } from 'react'
import Modal from '../modal/Modal'
import axios from 'axios';
import { useSelector } from 'react-redux';

function OrderForm({setIsOpen,book}) {
    const user = useSelector(state=>state);
    console.log("user : ")
    console.log(user);
    const [address,setAddress] = useState(getAddr());
    const [quantity,setQuantity] = useState(1);
    function getAddr(){
        if(user && user.address && user.address !==''){
            return user.address;
        }
        return ''
    }
    async function handleBuy(e){
        e.preventDefault();
        const body = {
            book : book,
            user : {
              username : user.name,
              address : address,
              pno: user.pno
            },
            quantity : quantity
          }
          console.log(body);
          const res = await axios.post('http://localhost:8080/api/item/addToOrder',body);
          if(res.status === 200){
            const newBook = book;
            newBook.availableQuantity = newBook.availableQuantity - quantity;
            axios.put(`http://localhost:8080/api/admin/books/${book.id}`,newBook)
          }
          setIsOpen(false);
    }
  return (
    <Modal setIsOpen = {setIsOpen}>
        <form className='add-book-form'>
            {(address==='') && <p>Your account does not have an address</p>}
            <label htmlFor="">Address</label>
            <textarea name="" id="" cols="30" rows="10" value={address} onChange={(e)=>setAddress(e.target.value)}></textarea>
            <div className='quantity'>
            <p>Qty : </p>
            
            {(quantity>1) &&  <button className='qty-controller' onClick={(e)=>{
              e.preventDefault();
                if(quantity>1)
                    setQuantity(prev=>prev-1)
                }}>
                    -</button>}
           
            {quantity}
            {quantity<book.availableQuantity && <button className='qty-controller' onClick={(e)=>{
              e.preventDefault();
                if(quantity<book.availableQuantity){
                  setQuantity(prev=>prev+1)
                }
                
                }}>+</button>}
            </div>
            <button className='buy-button' style={{margin:0}} onClick={(e)=>handleBuy(e)}>Buy</button>
        </form>
    </Modal>
  )
}

export default OrderForm