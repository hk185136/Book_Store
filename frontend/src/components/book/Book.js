import React, { useEffect, useState } from 'react'
import './Book.css';
import {useSelector} from 'react-redux';
import Modal from '../modal/Modal';
import axios from 'axios';
import OrderForm from '../OrderForm/OrderForm';
import { toast } from 'react-toastify';
function Book({isInCart,cartId,book,deleteBook,editBook,cartItems,setCartItems}) {
  console.log('is in cart : ' + isInCart)
    const [isAdded,setIsAdded] = useState(isInCart);
    console.log('isAdded: ' + isAdded)
    const user = useSelector(state=>state)
    const [isOpen,setIsOpen] = useState(false);
    const [newBook,setNewBook] = useState(book);
    const [showPopup,setShowPopup] = useState(false);
    const [quantity,setQuantity] = useState(1);
    const [orderOpen,setOrderOpen] = useState(false);
    const largeTitle = {fontSize : 'large'}
    const mediumTitle = {fontSize : 'larger'}
    const smallTitle = {fontSize : 'xx-large'}
    useEffect(()=>{
      console.log('use effect : '+isInCart);
      setIsAdded(isInCart);
    },[isInCart])
    function togglePopup(e){
      setShowPopup(true);
      if(book.availableQuantity===0){
        setTimeout(()=>{
          setShowPopup(false);
        },1000)
      }
    }
    // console.log(orderOpen);
    async function handleBuy(){
      setOrderOpen(true)
    }
    async function handleAddToCart(){
      const res = await axios.post('http://localhost:8080/api/item/addToCart',{
        book : book,
        user : {username : user.name},
        quantity : quantity
      })
      if(res.status == 200){
        setCartItems([...cartItems,res.data])
        setIsAdded(true);
        setShowPopup(false);
      }
      
    }
    async function handleRemoveFromCart(){
      try{
        const res = await axios.delete('http://localhost:8080/api/item/' + cartId);
        const newCartItems = cartItems.filter((item)=>item.id!==cartId);
        setCartItems(newCartItems);
        if(res.status == 200){
          setIsAdded(false);
        }
      }
      catch(e){
        toast.error((e?.response?.data?.message) || (e.message));
      }
     
      
    }
    function assignStyle(title){
      if(title.length<10){
        return smallTitle;
      }
      else if(title.length<20){
        return mediumTitle;
      }
      return largeTitle;
    }
  return (
    <div className='book-card'>
        <div className='book-img-container'>
            <img src={book.url} alt="No cover image" className='book-img'/>
        </div>
        <div className='book-details'>
            <p className='book-name' style={assignStyle(book.title)}>{book.title}</p>
            <p className='author-name'>{book.author.length>0 && <>By author : {book.author}</>}</p>
            <p className='price'>&#8377;{book.price}</p>
            {(user.role === 'admin') && (<>
              <button className='edit-button' onClick={()=>setIsOpen(true)}>edit</button>
              <img className='delete' src="delete-button.png" alt="" onClick={()=>deleteBook(book)}/>
            </>)  }
            {(user.role === 'customer') && (book.availableQuantity>0) &&
            <>
            {book.availableQuantity>0 && <>
             <button className='buy-button' onClick={handleBuy}>Buy</button>
            {(isAdded)?(<img  onClick={(e)=>
              {
                handleRemoveFromCart();
              }
              } className='cart-img' src="/remove-from-cart.svg" alt="" />):(<img  onClick={(e)=>{togglePopup(e)}} className='cart-img' src="/add-to-cart.png" alt="" />)}</>}

            
            </>}
            {
              (user.role === 'customer') && book.availableQuantity===0 && <p style={{marginTop:20}}>No copies available</p>
            }
            {(showPopup)&& (
              <div className='qty-popup' style={{left : 150,top : 130}}>
                <img src="/close-icon.jpg" alt="" onClick={()=>setShowPopup(false) } className='closePopup'/>
                {(book.availableQuantity==0)?(<p>No copies available</p>):
                (<>
                <p>Avaialable copies : {book.availableQuantity}</p>
              <div className='quantity' style={{marginTop : 0,marginBottom : 0}}>
                <p style={{marginRight : 5}}>Qty : </p>
                {(quantity>1) &&  <button className='qty-controller' onClick={()=>{
                    if(quantity>1)
                        setQuantity(prev=>prev-1);
                    }}>
                        -</button>}
               
                {quantity}
                {(quantity<book.availableQuantity) && <button className='qty-controller' onClick={()=>{
                  if(quantity<book.availableQuantity){
                    setQuantity(prev=>prev+1)
                  }
                  
                  }}>+</button>}
                
                  
                  </div>
                  <button onClick={handleAddToCart} className='add'>Add</button>
                </>)}
              
            
              
            </div>
            )}
            
           
           
        </div>
        {(isOpen)&&
        <Modal setIsOpen = {setIsOpen}>
                <form className='add-book-form'>
                    <label htmlFor="">Book name</label>
                    <input type="text" className='add-book-input' value={newBook.title}  onChange={(e)=>{setNewBook({...newBook,title : e.target.value})}}/>
                    <label htmlFor="">Author</label>
                    <input type="text" name='author' className='add-book-input' value={newBook.author} onChange={(e)=>{setNewBook({...newBook,author : e.target.value})}}/>
                    <label htmlFor="">Genre</label>
                    <input type="text" name='genre' className='add-book-input' value={newBook.genre} onChange={(e)=>setNewBook({...newBook,genre : e.target.value})}/>
                    <label htmlFor="">Book image url</label>
                    <input type="text" name='url' className='add-book-input' value={newBook.url} onChange={(e)=>setNewBook({...newBook,url : e.target.value})}/>
                    <label htmlFor="">Price</label>
                    <input type="text" name='price' className='add-book-input' value={newBook.price} onChange={(e)=>setNewBook({...newBook,price : e.target.value})}/>
                    <label htmlFor="">Quantity</label>
                    <input type="text" name='quantity' className='add-book-input' value={newBook.availableQuantity} onChange={(e)=>setNewBook({...newBook,availableQuantity : e.target.value})}/>
                    <button type='submit' className='buy-button' onClick={(e)=>{e.preventDefault();
                    setIsOpen(false);
                      editBook(book.id,newBook)}}> Update </button>
                </form>
            </Modal>
            

}
{(orderOpen) && (<OrderForm setIsOpen={setOrderOpen} book = {book}/>)}
    </div>
  )
}

export default Book