function DeleteModal({ name, onCancel, onConfirm }) {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black/40 backdrop-blur-md z-50">
      <div className="bg-white rounded-lg shadow-xl p-6 w-96">
        <h2 className="text-xl font-bold mb-4 flex items-center gap-2">
          ⚠ Delete Employee
        </h2>

        <p className="mb-6">
          Are you sure you want to delete <strong>{name}</strong>?
        </p>

        <div className="flex justify-end gap-4">
          <button
            onClick={onCancel}
            className="px-4 py-2 rounded bg-gray-300 hover:bg-gray-400"
          >
            No
          </button>

          <button
            onClick={onConfirm}
            className="px-4 py-2 rounded bg-red-500 text-white hover:bg-red-600"
          >
            Yes
          </button>
        </div>
      </div>
    </div>
  );
}

export default DeleteModal;