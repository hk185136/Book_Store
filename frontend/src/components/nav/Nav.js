import React, { useContext, useState } from 'react';
import './Nav.css';
import {Link} from 'react-router-dom'
import Modal from '../modal/Modal';
import { userContext } from '../../UserContext';
import axios from 'axios'
function Nav({books,setBooks}) {
    const [searchBy,setSearchBy] = useState('book');
    const [isOpen,setIsOpen] = useState(false);
    const [search,setSearch] = useState('');
    const [user,setUser] = useContext(userContext);
    const initialState = {
        title : '',
        url : '',
        author : '',
        genre : '',
        price : ''
    }
    const [newBook,setNewBook] = useState(initialState);
    async function handleAdd(e){
        e.preventDefault();
        try{
            e.preventDefault();
            setNewBook(initialState);
            const res = await axios.post('http://localhost:8080/api/admin/books/',newBook);
            setBooks([...books,res.data])
        }
        catch(e){
            alert(e.message);
        }
    }
    async function handleSearch(){
        if(search.length>0){
        try{
            const res = await axios.get(`http://localhost:8080/api/user/books/title/${search}`);
            if(res.status == 200){
                setBooks(res.data)
            }
            
        }
        catch(e){
            alert(e.message);
        }
    }
    }

  return (
    <div className='nav'>
        <Link to={'/home'} id='site-name'><h1>Book Store</h1></Link>
        <div className='search-container'>
            <input type="text" placeholder='Search ...' className='search-bar' value={search} onChange={(e)=>setSearch(e.target.value)}/>
            <span><button onClick={handleSearch}>search</button></span>
            <p className='search-by-text'>Search by : </p>
            <select name="" id="search-by" value={searchBy} onChange={(e)=>setSearchBy(e.target.value)}>
                <option value="book">book</option>
                <option value="author">author</option>
            </select>
        </div>
       
        <div className='right-component'>
            {(user.role === 'admin') && <img src="/add-icon.png" alt="" className='nav-img' onClick={()=>setIsOpen(true)}/>}
            {(user.role === 'customer') && <Link to={'/home/cart'}><img src="/shopping-cart.png" alt="" className='nav-img'/></Link>}
            <Link to={'/home/profile'}><img src="/profile.png" alt="" className='nav-img' /></Link>
            <Link to={'/'}><img src="/logout.png" alt="" className='nav-img' onClick={()=>setUser(null)} /></Link> 
        </div>
        {(isOpen) &&
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
                    <button type='submit' className='add-book-button' onClick={(e)=>handleAdd(e)}> Add </button>
                </form>
            </Modal>
        }
        
    </div>
  )
}

export default Nav