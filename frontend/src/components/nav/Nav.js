import React, { useState } from 'react';
import './Nav.css';
import {Link} from 'react-router-dom'
import Modal from '../modal/Modal';
function Nav() {
    const [searchBy,setSearchBy] = useState('book');
    const [isOpen,setIsOpen] = useState(false);
  return (
    <div className='nav'>
        <h1 id='site-name'>Book Store</h1>
        <div className='search-container'>
            <input type="text" placeholder='Search ...' className='search-bar'/>
            <p className='search-by-text'>Search by : </p>
            <select name="" id="search-by" value={searchBy} onChange={(e)=>setSearchBy(e.target.value)}>
                <option value="book">book</option>
                <option value="author">author</option>
            </select>
        </div>
       
        <div className='right-component'>
            <img src="/add-icon.png" alt="" className='nav-img' onClick={()=>setIsOpen(true)}/>
            <Link to={'/cart'}><img src="/shopping-cart.png" alt="" className='nav-img'/></Link>
            <Link to={'/profile'}><img src="/profile.png" alt="" className='nav-img' /></Link>
            <Link to={'/'}><img src="/logout.png" alt="" className='nav-img' /></Link> 
        </div>
        {(isOpen) &&
            <Modal setIsOpen = {setIsOpen}>
                <form className='add-book-form'>
                    <label htmlFor="">Book name</label>
                    <input type="text" className='add-book-input' />
                    <label htmlFor="">Author</label>
                    <input type="text" className='add-book-input'/>
                    <label htmlFor="">Book image url</label>
                    <input type="text" className='add-book-input'/>
                    <label htmlFor="">Price</label>
                    <input type="text" className='add-book-input'/>
                </form>
            </Modal>
        }
        
    </div>
  )
}

export default Nav