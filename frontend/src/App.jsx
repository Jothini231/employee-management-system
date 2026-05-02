import React from "react";
import SideBar from "./components/SideBar";
import { BrowserRouter, Route, Routes } from "react-router-dom";

// Pages
import Employees from "./pages/employees/Employees";
import Department from "./pages/departments/Department";

// Employee Components
import EmployeeProfile from "./components/employee/EmployeeProfile";
import EmployeeForm from "./components/employee/EmployeeForm";

const App = () => {
  return (
    <BrowserRouter>
      <div className="flex">
        <SideBar />

        <div className="flex-1 p-6">
          <Routes>
            {/* Employee Routes */}
            <Route path="/employees" element={<Employees />} />
            <Route path="/employees/add" element={<EmployeeForm />} />
            <Route path="/employees/edit/:id" element={<EmployeeForm />} />
            <Route path="/employees/view/:id" element={<EmployeeProfile />} />

            {/* Department */}
            <Route path="/departments" element={<Department />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;