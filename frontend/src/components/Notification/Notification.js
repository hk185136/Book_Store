import React from 'react'
import { MdDelete } from "react-icons/md";
function Notification({notification,handleDelete}) {
  return (
    <tr className='Notification'>
      <td><p>{notification.date}</p></td>
      <td>{notification.message}</td>
      <td><MdDelete onClick={()=>handleDelete(notification.id)} className='message-delete-button'/></td>
    </tr>
  )
}

export default Notification