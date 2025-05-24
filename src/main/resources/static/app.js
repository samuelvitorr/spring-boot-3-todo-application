// Função para avançar o status da tarefa (Backlog → Doing → Done)
const onHandleListItemClick = async (id) => {
    console.log('click ', id);

    const options = {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: {} // Sem corpo, pois só altera status no backend
    };

    const response = await fetch(`/todos/${id}`, options);
    const data = await response.json();
    console.log('data', data);
    window.location.reload(); // Atualiza a página para mostrar o novo status
};

// Função para deletar uma tarefa pelo id
const deleteTodo = async (id) => {
    console.log('Deleting todo with id:', id);

    const options = {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
    };

    const response = await fetch(`/todos/${id}`, options);
    if (response.ok) {
        window.location.reload(); // Atualiza a página após exclusão
    } else {
        console.error('Failed to delete todo:', response.statusText);
    }
};

// Função para editar a descrição da tarefa
const editTodo = async (id) => {
    const newDescription = prompt("Digite a nova descrição da tarefa:");
    if (newDescription) {
        const options = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ description: newDescription }) // Envia a nova descrição no corpo JSON
        };
        // Note que aqui o endpoint correto é /todos/edit/{id}
        const response = await fetch(`/todos/edit/${id}`, options);
        if (response.ok) {
            window.location.reload(); // Atualiza a página após edição
        } else {
            console.error('Failed to edit todo:', response.statusText);
        }
    }
};

// Função que roda quando o DOM está pronto (jQuery)
$(() => {
    console.log('jquery ready');
});
