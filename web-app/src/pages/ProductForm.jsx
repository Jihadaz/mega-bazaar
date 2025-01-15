import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import instance from '../services/api';

const ProductForm = () => {
  const [formData, setFormData] = useState({
    label: '',
    description: '',
    price: '',
    quantity: '',
    code: '',
    active: false,
    categoryId: '',
  });
  const [categories, setCategories] = useState([]);
  const [file, setFile] = useState(null);
  const navigate = useNavigate();

  // Fetch categories on component mount
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await instance.get('/categories');
        setCategories(response.data);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };
    fetchCategories();
  }, []);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  // Handle file input changes
  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Prepare form data
    const data = new FormData();
    Object.keys(formData).forEach((key) => {
      if (key === 'categoryId' && formData[key]) {
        data.append('categoryDto.id', formData[key]); // Map categoryId to categoryDto.id
      } else {
        data.append(key, formData[key]);
      }
    });
    if (file) data.append('file', file);

    try {
      await instance.post('/products/save', data, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      alert('Product created successfully');
      navigate('/');
    } catch (error) {
      console.error('Error creating product:', error);
      alert('Failed to create product');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium">Label</label>
        <input
          type="text"
          name="label"
          value={formData.label}
          onChange={handleChange}
          className="w-full border rounded px-2 py-1"
          placeholder="Enter product label"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium">Description</label>
        <textarea
          name="description"
          value={formData.description}
          onChange={handleChange}
          className="w-full border rounded px-2 py-1"
          placeholder="Enter product description"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium">Price</label>
        <input
          type="number"
          name="price"
          value={formData.price}
          onChange={handleChange}
          className="w-full border rounded px-2 py-1"
          placeholder="Enter product price"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium">Quantity</label>
        <input
          type="number"
          name="quantity"
          value={formData.quantity}
          onChange={handleChange}
          className="w-full border rounded px-2 py-1"
          placeholder="Enter product quantity"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium">Code</label>
        <input
          type="text"
          name="code"
          value={formData.code}
          onChange={handleChange}
          className="w-full border rounded px-2 py-1"
          placeholder="Enter product code"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium">Category</label>
        <select
          name="categoryId"
          value={formData.categoryId}
          onChange={handleChange}
          className="w-full border rounded px-2 py-1"
          required
        >
          <option value="">Select a category</option>
          {categories.map((category) => (
            <option key={category.id} value={category.id}>
              {category.label}
            </option>
          ))}
        </select>
      </div>
      <div>
        <label className="block text-sm font-medium">Image</label>
        <input
          type="file"
          name="file"
          onChange={handleFileChange}
          className="w-full border rounded px-2 py-1"
        />
      </div>
      <div className="flex items-center">
        <input
          type="checkbox"
          name="active"
          checked={formData.active}
          onChange={handleChange}
          className="mr-2"
        />
        <label className="text-sm">Active</label>
      </div>
      <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
        Save
      </button>
    </form>
  );
};

export default ProductForm;
