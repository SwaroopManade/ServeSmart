import axios from 'axios';

// Set up a base URL for all API calls
const api = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL || 'http://localhost:8080',
});

// Interceptor to set the Authorization header if token exists
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken'); // Replace with your logic
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// API service methods for Tables
export const getAllTables = async () => {
  try {
    const response = await api.get('/tables');
    return response.data.data; // Access the 'data' field to get the array of tables
  } catch (error) {
    console.error('Error fetching tables:', error);
    throw error;
  }
};

export const getTableById = async (id) => {
  try {
    const response = await api.get(`/tables/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching table with ID ${id}:`, error);
    throw error;
  }
};

export const createTable = async (tableData) => {
  try {
    const response = await api.post('/tables', tableData);
    return response.data;
  } catch (error) {
    console.error('Error creating table:', error);
    throw error;
  }
};

export const updateTable = async (id, tableData) => {
  try {
    const response = await api.put(`/tables/${id}`, tableData);
    return response.data;
  } catch (error) {
    console.error(`Error updating table with ID ${id}:`, error);
    throw error;
  }
};

export const deleteTable = async (id) => {
  try {
    const response = await api.delete(`/tables/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error deleting table with ID ${id}:`, error);
    throw error;
  }
};

// API service methods for Dishes
export const getAllDishes = async () => {
  try {
    const response = await api.get('/api/dishes'); // Adjust endpoint as necessary
    return response.data; // Ensure this returns the array of dishes
  } catch (error) {
    console.error('Error fetching dishes:', error);
    throw error;
  }
};

export const getDishById = async (id) => {
  try {
    const response = await api.get(`/api/dish/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching dish with ID ${id}:`, error);
    throw error;
  }
};

export const createDish = async (dishData) => {
  try {
    const response = await api.post('/api/dish', dishData);
    return response.data;
  } catch (error) {
    console.error('Error creating dish:', error);
    throw error;
  }
};

export const updateDish = async (id, dishData) => {
  try {
    const response = await api.put(`/api/dish/${id}`, dishData);
    return response.data;
  } catch (error) {
    console.error(`Error updating dish with ID ${id}:`, error);
    throw error;
  }
};

export const deleteDish = async (id) => {
  try {
    const response = await api.delete(`/api/dish/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error deleting dish with ID ${id}:`, error);
    throw error;
  }
};

// API service methods for Orders
export const saveOrder = async (orderData) => {
  try {
    const response = await api.post('/orders', orderData); // Adjust endpoint as necessary
    return response.data;
  } catch (error) {
    console.error('Error saving order:', error);
    throw error;
  }
};

// export const deleteOrder = async (orderId) => {
//   try {
//     const response = await api.delete(`/orders/${orderId}`); // Adjust endpoint as necessary
//     return response.data;
//   } catch (error) {
//     console.error(`Error deleting order with ID ${orderId}:`, error);
//     throw error;
//   }
// };

export const getOrderByTableId = async (tableId) => {
  try {
    const response = await api.get(`/orders/table/${tableId}`);
    return response.data; // Return the existing order data for the table
  } catch (error) {
    console.error(`Error fetching order for table ${tableId}:`, error);
    throw error;
  }
};

export const updateOrder = async (tableId, orderData) => {
  try {
    const response = await api.put(`/orders/update/table/${tableId}`, orderData); // using orderId now
    return response.data; // Return the updated order data
  } catch (error) {
    console.error(`Error updating order with ID ${tableId}:`, error);
    throw error;
  }
};



export const deleteOrder = async (tableId) => {
  try {
    const response = await api.delete(`/orders/delete/${tableId}`);
    return response.data; // Confirm deletion
  } catch (error) {
    console.error(`Error deleting order for table ${tableId}:`, error);
    throw error;
  }
};

// ApiService.js

// export const getOrderByTableId = async (tableId) => {
//   return await axios.get(`/orders/table/${tableId}`);
// };

// New function to fetch by orderId
export const getOrderByOrderId = async (orderId) => {
  try {
    const response = await api.get(`/orders/order/${orderId}`);
    console.log("Order is fetched");
    
    return response.data;
  } catch (error) {
    console.error('Error fetching order:', error);
    throw error;
  }
};

export const deleteOrdersByTableId = async (tableId) => {
  return await axios.delete(`http://localhost:8080/orders/clear/${tableId}`);
};

// Add other order-related methods as necessary
