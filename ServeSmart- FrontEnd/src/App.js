// App.js
import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import WelcomePage from './components/WelcomePage';
import AddTable from './components/AddTable';
import AddDish from './components/AddDish';
import UpdateOrder from './components/UpdateOrder';
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
  const [tables, setTables] = useState([]); // Manages list of tables
  const [dishes, setDishes] = useState([]); // Manages list of dishes
  const [selectedTable, setSelectedTable] = useState(null); // Tracks selected table for ordering

  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          <Route path="/" element={<WelcomePage tables={tables} setTables={setTables} setSelectedTable={setSelectedTable} />} />
          <Route path="/add-table" element={<AddTable tables={tables} setTables={setTables} />} />
          <Route path="/add-dish" element={<AddDish dishes={dishes} setDishes={setDishes} />} />
          <Route path="/update-order" element={<UpdateOrder selectedTable={selectedTable} tables={tables} setTables={setTables} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
