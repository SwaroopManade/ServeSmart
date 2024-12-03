import React, { useState } from 'react';
import { createTable } from '../service/ApiService'; // Adjust the import path as needed
import { Alert } from 'react-bootstrap'; // Import Bootstrap's Alert component

const AddTable = () => {
  const [tableNumber, setTableNumber] = useState('');
  const [status, setStatus] = useState('vacant'); // Default status
  const [capacity, setCapacity] = useState('');
  const [successMessage, setSuccessMessage] = useState(''); // State for success message
  const [errorMessage, setErrorMessage] = useState(''); // State for error message

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newTable = { 
      tableNumber: parseInt(tableNumber), // Ensure it's a number
      status,
      capacity: parseInt(capacity) // Ensure it's a number
    };
    
    try {
      await createTable(newTable);
      setSuccessMessage('Table added successfully!'); // Set success message
      setTableNumber('');
      setStatus('vacant'); // Reset to default
      setCapacity('');
      setErrorMessage(''); // Clear any previous error message
    } catch (error) {
      console.error('Error creating table:', error);
      setErrorMessage('Error adding table. Please try again.'); // Set error message
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4">Add New Table</h2>
      {successMessage && <Alert variant="success">{successMessage}</Alert>} {/* Success message */}
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>} {/* Error message */}
      <form onSubmit={handleSubmit} className="row g-3">
        <div className="col-md-4">
          <input
            type="number"
            className="form-control"
            value={tableNumber}
            onChange={(e) => setTableNumber(e.target.value)}
            placeholder="Table Number"
            required
          />
        </div>
        <div className="col-md-4">
          <select className="form-select" value={status} onChange={(e) => setStatus(e.target.value)}>
            <option value="vacant">Vacant</option>
            <option value="occupied">Occupied</option>
          </select>
        </div>
        <div className="col-md-4">
          <input
            type="number"
            className="form-control"
            value={capacity}
            onChange={(e) => setCapacity(e.target.value)}
            placeholder="Capacity"
            required
          />
        </div>
        <div className="col-12">
          <button type="submit" className="btn btn-primary">Add Table</button>
        </div>
      </form>
    </div>
  );
};

export default AddTable;
