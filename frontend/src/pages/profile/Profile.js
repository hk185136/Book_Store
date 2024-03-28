import React, { useState } from 'react'
import Orders from '../../pages/orders/Orders';
import './Profile.css'
import Modal from '../../components/modal/Modal';
import axios from 'axios';
function Profile() {
  const user=JSON.parse(localStorage.getItem('user'));
  const [pno,setPno] = useState(user.pno || '');
  const [address,setAddress] = useState(user.address || '');
  const [isOpen,setIsOpen] = useState(false);
  async function editProfile(e){
    e.preventDefault(); 
    axios.put(`http://localhost:8080/editUser/${user.name}`,{
      username:user.name,
      pno:pno,
      address:address
    })

  }
  return (
    <div className='profile-page-container'>
      <div className='profile-page'>
        <p>Username</p>
        <p>-</p>
        <p>{user.name}</p>
        <p>Phone No.</p>
        <p>-</p>
        <p>{(pno.length>0)?pno:"No contact info"}</p>
        <p>Delivery address</p>
        <p>-</p>
        <p>{(address.length>0)?address:"No address info"}</p>
        <button onClick={()=>setIsOpen(true)}>Edit profile</button>
      </div>
      {(isOpen) && <Modal setIsOpen = {setIsOpen}>
        <form action="">
        <p>Phone No.</p>
        <input type="text" value={user.pno} onChange={(e)=>setPno(e.target.value)}/>
        <p>Delivery address</p>
        <textarea type="text" rows={3} value={user.address} onChange={(e)=>setAddress(e.target.value)}/>
        <br />
        <button onClick={(e)=>editProfile(e)}>Edit profile</button>
        </form>
    </Modal>}
      
    </div>
    
    
  )
}

export default Profile