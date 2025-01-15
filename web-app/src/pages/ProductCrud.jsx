import React, { useEffect, useState } from 'react';
import instance from '../services/api'; 
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';

const ProductCrud = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    setLoading(true);
    try {
      const response = await instance.get('/products');
      setProducts(response.data.products); // Adjust response structure as per your API
      setLoading(false);
    } catch (error) {
      console.error('Error fetching products:', error);
      toast.error('Failed to load products. Please try again later.');
      setLoading(false);
    }
  };

  const handleDelete = async (productId) => {
    const confirmDelete = window.confirm('Are you sure you want to delete this product?');
    if (!confirmDelete) return;

    try {
      await instance.delete(`/products/${productId}/delete`);
      toast.success('Product deleted successfully!');
      setProducts((prevProducts) => prevProducts.filter((product) => product.id !== productId));
    } catch (error) {
      console.error('Error deleting product:', error);
      toast.error('Failed to delete product. Please try again.');
    }
  };

  if (loading) {
    return (
      <div className="container mx-auto p-8 text-center">
        <div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-blue-500 mx-auto mb-4"></div>
        <p className="text-gray-700">Loading products...</p>
      </div>
    );
  }

  if (products.length === 0) {
    return (
      <div className="container mx-auto p-8 text-center">
        <h2 className="text-2xl font-bold text-gray-700">No Products Found</h2>
        <p className="text-gray-500">Try adding some products or refresh the page.</p>
      </div>
    );
  }

  return (
    <div className="container mx-auto p-8">
        <div className='flex justify-between  mb-6'>
            <h1 className="text-3xl font-bold text-gray-800">Product List</h1>
            <div className='space-x-6'>
            <Link to="/product/new" className=" bg-lime-300 hover:bg-lime-400 text-gray-700 font-medium py-2 px-4 rounded-md transition duration-300">
              Add product
            </Link>
            <Link to="/category/new" className=" bg-lime-300 hover:bg-lime-400 text-gray-700 font-medium py-2 px-4 rounded-md transition duration-300">
              Add Category
            </Link>
            </div>
        </div>
        
      <table className="table-auto w-full border-collapse border border-gray-300">
        <thead>
          <tr className="bg-gray-200">
            <th className="border border-gray-300 px-4 py-2">ID</th>
            <th className="border border-gray-300 px-4 py-2">Label</th>
            <th className="border border-gray-300 px-4 py-2">Description</th>
            <th className="border border-gray-300 px-4 py-2">Price</th>
            <th className="border border-gray-300 px-4 py-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.id} className="hover:bg-gray-100">
              <td className="border border-gray-300 px-4 py-2 text-center">{product.id}</td>
              <td className="border border-gray-300 px-4 py-2">{product.label}</td>
              <td className="border border-gray-300 px-4 py-2">{product.description}</td>
              <td className="border border-gray-300 px-4 py-2 text-right">${product.price.toFixed(2)}</td>
              <td className="border border-gray-300 px-4 py-2 text-center">
                <button
                  onClick={() => handleDelete(product.id)}
                  className="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-3 rounded"
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProductCrud;
