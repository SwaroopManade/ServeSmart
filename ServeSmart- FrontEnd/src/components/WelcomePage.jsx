import React, { useEffect, useState } from 'react'; 
import { getAllTables, createTable, deleteTable } from '../service/ApiService'; 
import Table from './Table';
import OrderModal from './OrderModal'; 
import 'bootstrap/dist/css/bootstrap.min.css'; 

function WelcomePage() {
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null); 
  const [orders, setOrders] = useState({}); 

  useEffect(() => {
    const fetchTables = async () => {
      try {
        const allTables = await getAllTables();
        setTables(allTables.sort((a, b) => a.tableNumber - b.tableNumber)); 
      } catch (error) {
        console.error('Error fetching tables:', error);
      }
    };

    fetchTables();
  }, []);

  const handleAddTable = async () => {
    const newTableNumber = tables.length > 0 ? tables[tables.length - 1].tableNumber + 1 : 1; 
    const newTable = {
      tableNumber: newTableNumber,
      status: 'Vacant',
      capacity: 4,
    };

    try {
      await createTable(newTable);
      const updatedTables = await getAllTables();
      setTables(updatedTables.sort((a, b) => a.tableNumber - b.tableNumber)); 
    } catch (error) {
      console.error('Error adding new table:', error);
    }
  };

  const handleDeleteTable = async (tableId) => {
    try {
      await deleteTable(tableId);
      const updatedTables = await getAllTables();
      setTables(updatedTables.sort((a, b) => a.tableNumber - b.tableNumber)); 
    } catch (error) {
      console.error('Error deleting table:', error);
    }
  };

  const handleTakeOrder = (table) => {
    setSelectedTable(table); 
    // Update the table status to Occupied
    setTables(prevTables => 
      prevTables.map(t => 
        t.id === table.id ? { ...t, status: 'Occupied' } : t
      )
    );
  };

  const closeModal = () => {
    setSelectedTable(null);   
  };

  const handleCheckout = (tableId, selectedDishes, totalAmount) => {
    setOrders((prev) => ({
      ...prev,
      [tableId]: {
        selectedDishes,
        totalAmount,
      },
    }));

    // Set the table status back to Vacant on checkout
    setTables(prevTables => 
      prevTables.map(t => 
        t.id === tableId ? { ...t, status: 'Vacant' } : t
      )
    );
  };
  
  return (
    <div className="container mt-4">
      <h1 className="text-center mb-4">Welcome to the Restaurant</h1>
      <button className="btn btn-primary mb-3" onClick={handleAddTable}>
        Add New Table
      </button>
      <div className="row">
        {tables.length > 0 ? (
          tables.map((table) => (
            <div className="col-md-4 mb-3" key={table.id}>
              <Table 
                table={table} 
                setSelectedTable={() => handleTakeOrder(table)} 
                handleDeleteTable={handleDeleteTable} 
              />
            </div>
          ))
        ) : (
          <p>No tables found.</p>
        )}
      </div>
      {selectedTable && (
        <OrderModal 
          table={selectedTable} 
          closeModal={closeModal} 
          orders={orders}  
          onCheckout={handleCheckout} 
        />
      )}
    </div>
  );
}

export default WelcomePage;
