import React, { useState } from 'react';
import { createDish } from '../service/ApiService'; // Adjust the import path as needed

const AddDish = () => {
  const [dishName, setDishName] = useState('');
  const [price, setPrice] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newDish = { name: dishName, price: price };
    await createDish(newDish);
    // Reset the form or update UI as necessary
    setDishName('');
    setPrice('');
  };

  return (
    <div>
      <h2>Add New Dish</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={dishName}
          onChange={(e) => setDishName(e.target.value)}
          placeholder="Dish Name"
          required
        />
        <input
          type="number"
          value={price}
          onChange={(e) => setPrice(e.target.value)}
          placeholder="Price"
          required
        />
        <button type="submit">Add Dish</button>
      </form>
    </div>
  );
};

export default AddDish;
