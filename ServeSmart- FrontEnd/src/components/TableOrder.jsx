// TableOrder.js
import React from 'react';

function TableOrder({ table }) {
  const totalAmount = table.dishes.reduce((total, dish) => total + dish.price, 0);

  return (
    <div>
      <h3>Order for Table {table.id}</h3>
      <ul>
        {table.dishes.map((dish, index) => (
          <li key={index}>{dish.name} - ${dish.price}</li>
        ))}
      </ul>
      <h4>Total Amount: ${totalAmount}</h4>
    </div>
  );
}

export default TableOrder;
