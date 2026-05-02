import React, { useEffect, useState } from "react";

export default function DepartmentModal({ close,onSave,initialData }) {
  const [form, setForm] = useState({
    name: "",
    departmentCode: "",
    description: "",
    status: true,
  });

 
  

  useEffect(() => {
   
    if(initialData){
       console.log('Initial data received:', initialData);
      setForm({
        name: initialData.name || "",
        departmentCode: initialData.departmentCode || "",
        description: initialData.description || "",
        status: initialData.status ?? true,
      });
      console.log('Form after setting:', form);

    }
  },[initialData]);



  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    const data = initialData ? {...form,id:initialData.id} : form;
    onSave(data);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center backdrop-blur-sm bg-black/30">
      
      <div className="bg-white w-[600px] rounded-2xl shadow-xl p-6" onClick={(e) => e.stopPropagation()}>

       
        <h2 className="text-2xl font-bold text-center text-[#1E2A38] mb-6 mt-4">
          {initialData ? "Edit Department" : "Add Department"}
        </h2>

        
        <div className="grid grid-cols-2 gap-4">

          
          <div>
            <label className="text-gray-700 mb-1">
              Department Name
            </label>
            <input
              name="name"
              value={form.name}
              className="w-full p-2.5 border p-2 rounded-lg"
              onChange={handleChange}
            />
          </div>

        
          <div>
            <label className="text-gray-700 mb-1">
              Department Code
            </label>
            <input
              name="departmentCode"
              value={form.departmentCode}
              className="w-full p-2.5 border rounded-lg "
              onChange={handleChange}
            />
          </div>

    

        
          <div>
            <label className="text-gray-700 mb-1">
              Status
            </label>
            <select
              name="status"
              value={form.status}
              className="w-full p-2.5 border rounded-lg"
              onChange={handleChange}
            >
              <option value={true}>Active</option>
              <option value={false}>Inactive</option>
            </select>
          </div>

          
          <div className="col-span-2">
            <label className="text-gray-700 mb-1">
              Description
            </label>
            <textarea
              name="description"
              value={form.description}
              rows="3"
              className="w-full p-2.5 border rounded-lg"
              onChange={handleChange}
            />
          </div>
        </div>

       
        <div className="flex justify-end gap-3 mt-6">
          <button
            onClick={close}
            className="px-4 py-2 rounded bg-gray-300 hover:bg-gray-400"
          >
            Cancel
          </button>

          <button
            onClick={handleSubmit}
             className="px-4 py-2 rounded bg-[#1E2A38] hover:bg-[#2C3A4D] text-white"
          >
            Save
          </button>
        </div>
      </div>
    </div>
  );
}