import React, { useContext, useState } from 'react';
import './Nav.css';
import {Link} from 'react-router-dom'
import Modal from '../modal/Modal';
import axios from 'axios';
import { IoCartOutline } from "react-icons/io5";
import { CgProfile } from "react-icons/cg";
import { CiLogout } from "react-icons/ci";
import { BiBookAdd } from "react-icons/bi";
import { IoLibrary } from "react-icons/io5";
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
function Nav({books,setBooks}) {    const [searchBy,setSearchBy] = useState('book');
    const [isOpen,setIsOpen] = useState(false);
    const [search,setSearch] = useState('');
    const user = useSelector(state=>state);
    const dispatch = useDispatch();
    const initialState = {
        title : '',
        url : '',
        author : '',
        genre : '',
        price : '',
        availableQuantity : 1
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
            toast.error((e?.response?.data?.message) || (e.message));
        }
    }
    async function handleSearch(){
        if(search.length>0){
        try{
            if(searchBy=='book'){
                const res = await axios.get(`http://localhost:8080/api/user/books/title/${search}`);
                if(res.status == 200){
                    setBooks(res.data)
                }
            }
            else{
                const res = await axios.get(`http://localhost:8080/api/user/books/author/${search}`);
                if(res.status == 200){
                    setBooks(res.data)
                }
            }
            
        }
        catch(e){
            toast.error((e?.response?.data?.message) || (e.message));
        }
    }
    }

  return (
    <div className='nav'>
        <div>
        
        <Link to={'/home'} id='site-name'><IoLibrary className='nav-img'/><span>Book Store</span></Link>
        </div>
        
       
        <div className='search-container'>
            <input type="text" placeholder='Search ...' className='search-bar' value={search} onChange={(e)=>setSearch(e.target.value)}/>
            <span><img className='search-button' src='/search.png' onClick={handleSearch}></img></span>
            <p className='search-by-text'>Search by : </p>
            <select name="" id="search-by" value={searchBy} onChange={(e)=>setSearchBy(e.target.value)}>
                <option value="book">Book</option>
                <option value="author">Author</option>
            </select>
        </div>
       
        <div className='right-component'>
        {(user.role === 'customer') && <Link to={'/home/orders'} state={{username : user.name}} className='my-orders'>My orders</Link>}
        {(user.role === 'admin') && <Link to={'/home/users'} className='my-orders'>Manage orders</Link>}
            {(user.role === 'admin') && <BiBookAdd className='nav-img' onClick={()=>setIsOpen(true)}/>}
            {(user.role === 'customer'|| user.role==="user") && <Link to={'/home/cart'}><IoCartOutline className='nav-img'/></Link>}
            <Link to={'/home/profile'}><CgProfile className='nav-img' /></Link>
            <Link to={'/'}><CiLogout className='nav-img' onClick={()=>{dispatch({type : 'logout'})}} /></Link> 
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
                    <label htmlFor="">Quantity</label>
                    <input type="text" name='quantity' className='add-book-input' value={newBook.availableQuantity} onChange={(e)=>setNewBook({...newBook,availableQuantity : e.target.value})}/>
                    <button type='submit' className='buy-button' onClick={(e)=>{handleAdd(e);setIsOpen(false)}}> Add </button>
                </form>
            </Modal>
        }
        
    </div>
  )
}

export default Nav