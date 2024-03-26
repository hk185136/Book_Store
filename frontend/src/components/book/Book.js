import React, { useContext, useState } from 'react'
import './Book.css';
import { userContext } from '../../UserContext';
import Modal from '../modal/Modal';
function Book({book,deleteBook,editBook}) {
    const [isAdded,setIsAdded] = useState(false);
    const [user,setUser] = useContext(userContext);
    const [isOpen,setIsOpen] = useState(false);
    const [newBook,setNewBook] = useState(book);
    const [showPopup,setShowPopup] = useState(false);
    const [quantity,setQuantity] = useState(0);
    const largeTitle = {fontSize : 'large'}
    const mediumTitle = {fontSize : 'larger'}
    const smallTitle = {fontSize : 'xx-large'}
    function togglePopup(e){
      setShowPopup(true);
    }
    async function handleAddToCart(){
      //Post api call
      setIsAdded(true);
      setShowPopup(false);
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
            <p className='author-name'>By author : {book.author}</p>
            <p className='price'>&#8377;{book.price}</p>
            {(user.role === 'admin')?(<>
              <button className='edit-button' onClick={()=>setIsOpen(true)}>edit</button>
              <img className='delete' src="delete-button.png" alt="" onClick={()=>deleteBook(book)}/>
            </>)
            : <>
             <button className='buy-button'>Buy</button>
            {(isAdded)?(<img  onClick={(e)=>
              {
                setIsAdded(false);
              }
              } className='cart-img' src="/remove-from-cart.svg" alt="" />):(<img  onClick={(e)=>{togglePopup(e)}} className='cart-img' src="/add-to-cart.png" alt="" />)}
            </>}
            {(showPopup)&& (
              <div className='qty-popup' style={{left : 150,top : 130}}>
              <p>Avaialable copies : {book.availableQuantity}</p>
              <div className='quantity' style={{marginTop : 0,marginBottom : 0}}>
                <p>Qty : </p>
                <button className='qty-controller' onClick={()=>{
                    if(quantity>0)
                        setQuantity(prev=>prev-1);
                    }}>
                        -</button>
                {quantity}
                <button className='qty-controller' onClick={()=>{
                  if(quantity<book.availableQuantity){
                    setQuantity(prev=>prev+1)
                  }
                  
                  }}>+</button>
            </div>
              <button onClick={handleAddToCart} className='add'>Add</button>
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
                    <button type='submit' className='add-book-button' onClick={(e)=>{e.preventDefault();
                      editBook(book.id,newBook)}}> Update </button>
                </form>
            </Modal>

}
    </div>
  )
}

export default Book