document.addEventListener('DOMContentLoaded', function () {
    // Variables globales
    let apiCount = 1; // Comenzamos con 1 API

    // Elementos del DOM
    const apiTabsContainer = document.getElementById('api-tabs-container');
    const apiFieldsContainer = document.getElementById('api-fields-container');
    const addApiBtn = document.getElementById('add-api-btn');
    const form = document.getElementById('documentacion-form');

    // Función para agregar una nueva pestaña y campos de API
    function addApiTab() {
        apiCount++;
        const newIndex = apiCount - 1;

        // Crear nueva pestaña
        const newTab = document.createElement('button');
        newTab.type = 'button';
        newTab.className = 'api-tab whitespace-nowrap py-4 px-4 border-b-2 font-medium text-sm border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300';
        newTab.dataset.tabIndex = newIndex;
        newTab.textContent = `API ${apiCount}`;
        newTab.addEventListener('click', switchTab);

        // Agregar pestaña al contenedor
        apiTabsContainer.appendChild(newTab);

        // Crear nuevos campos para la API (con el nuevo campo de comentarios)
        const newApiFields = document.createElement('div');
        newApiFields.className = 'api-fields hidden';
        newApiFields.dataset.apiIndex = newIndex;
        newApiFields.innerHTML = `
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">
                        <i class="fas fa-signature mr-1 text-indigo-500"></i>Nombre de API
                    </label>
                    <input type="text" name="apis[${newIndex}][nombreApi]"
                        class="api-nombre mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">
                        <i class="fas fa-tag mr-1 text-indigo-500"></i>Tipo
                    </label>
                    <select name="apis[${newIndex}][tipo]"
                        class="api-tipo mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500">
                        <option value="REST">REST</option>
                        <option value="SOAP">SOAP</option>
                        <option value="GraphQL">GraphQL</option>
                    </select>
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">
                        <i class="fas fa-route mr-1 text-indigo-500"></i>Ruta
                    </label>
                    <input type="text" name="apis[${newIndex}][ruta]"
                        class="api-ruta mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">
                        <i class="fas fa-database mr-1 text-indigo-500"></i>Datos que regresa
                    </label>
                    <input type="text" name="apis[${newIndex}][datosRegresa]"
                        class="api-datos mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500">
                </div>
            </div>
            <div class="mt-6">
                <label class="block text-sm font-medium text-gray-700 mb-1">
                    <i class="fas fa-sign-in-alt mr-1 text-indigo-500"></i>Parámetros que recibe
                </label>
                <textarea name="apis[${newIndex}][parametros]" rows="3"
                    class="api-parametros mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"></textarea>
            </div>
            <div class="mt-6">
                <label class="block text-sm font-medium text-gray-700 mb-1">
                    <i class="fas fa-info-circle mr-1 text-indigo-500"></i>Descripción de la función
                </label>
                <textarea name="apis[${newIndex}][descripcion]" rows="3"
                    class="api-descripcion mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"></textarea>
            </div>
            <div class="mt-6">
                <label class="block text-sm font-medium text-gray-700 mb-1">
                    <i class="fas fa-comment mr-1 text-indigo-500"></i>Comentarios adicionales
                </label>
                <textarea name="apis[${newIndex}][comentariosAdicionales]" rows="3"
                    class="api-comentarios mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500"></textarea>
            </div>
            <div class="mt-4">
                <button type="button" class="remove-api-btn text-red-500 hover:text-red-700 text-sm">
                    <i class="fas fa-trash mr-1"></i> Eliminar esta API
                </button>
            </div>
        `;

        // Agregar campos al contenedor
        apiFieldsContainer.appendChild(newApiFields);

        // Agregar evento al botón de eliminar
        newApiFields.querySelector('.remove-api-btn').addEventListener('click', function () {
            removeApiTab(newIndex);
        });

        // Cambiar a la nueva pestaña
        switchTab({ currentTarget: newTab });
    }

    // Función para cambiar entre pestañas
    function switchTab(event) {
        const tabIndex = event.currentTarget.dataset.tabIndex;

        // Actualizar pestañas activas
        document.querySelectorAll('.api-tab').forEach(tab => {
            if (tab.dataset.tabIndex === tabIndex) {
                tab.classList.add('border-indigo-500', 'text-indigo-600');
                tab.classList.remove('border-transparent', 'text-gray-500', 'hover:text-gray-700', 'hover:border-gray-300');
            } else {
                tab.classList.remove('border-indigo-500', 'text-indigo-600');
                tab.classList.add('border-transparent', 'text-gray-500', 'hover:text-gray-700', 'hover:border-gray-300');
            }
        });

        // Mostrar campos correspondientes
        document.querySelectorAll('.api-fields').forEach(fields => {
            if (fields.dataset.apiIndex === tabIndex) {
                fields.classList.remove('hidden');
            } else {
                fields.classList.add('hidden');
            }
        });
    }

    // Función para eliminar una pestaña de API
    function removeApiTab(index) {
        if (apiCount <= 1) {
            alert("Debe haber al menos una API");
            return;
        }

        // Eliminar pestaña y campos
        document.querySelector(`.api-tab[data-tab-index="${index}"]`).remove();
        document.querySelector(`.api-fields[data-api-index="${index}"]`).remove();

        // Actualizar contador
        apiCount--;

        // Cambiar a la primera pestaña si eliminamos la actual
        const firstTab = document.querySelector('.api-tab');
        if (firstTab) {
            switchTab({ currentTarget: firstTab });
        }
    }

    // Función para enviar el formulario
    function handleSubmit(event) {
        event.preventDefault();

        // Recolectar datos del formulario
        const formData = new FormData(form);

        const data = {
            desarrollador: formData.get("desarrollador"),
            nombreModulo: formData.get('nombreModulo'),
            rutaModulo: formData.get('rutaModulo'),
            clasesUtiliza: formData.get('clasesUtiliza'),
            javascript: formData.get('javascript'),
            jsp: formData.get('jsp'),
            pojo: formData.get('pojo'),
            serviceRepo: formData.get('serviceRepo'),
            controladores: formData.get('controladores'),
            apis: [] // [{},{}]
        };
        // Procesar APIs 
        for (let i = 0; i < apiCount; i++) {
            const apiData = {
                nombreApi: formData.get(`apis[${i}][nombreApi]`),
                tipo: formData.get(`apis[${i}][tipo]`),
                ruta: formData.get(`apis[${i}][ruta]`),
                datosRegresa: formData.get(`apis[${i}][datosRegresa]`),
                parametros: formData.get(`apis[${i}][parametros]`),
                descripcion: formData.get(`apis[${i}][descripcion]`),
                comentariosAdicionales: formData.get(`apis[${i}][comentariosAdicionales]`)
            };
            data.apis.push(apiData);
        }

        console.log("Datos a enviar:", data);

        // Agrega el token CSRF al objeto data
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;

        // Llamada al endpoint del controlador
        // fetch('http://localhost:8080/debug-json', {
        //     method: 'POST', 
        //     headers: {
        //         'Content-Type': 'application/json',
        //         [csrfHeaderName]: csrfToken,  //X-CSRF-TOKEN para que srpign lo cache y le de acceso 
        //     },
        //     credentials: 'include',
        //     body: JSON.stringify(data)
        // })// response.blob(): Convierte la respuesta HTTP en un objeto Blob
        //     .then(response => response.blob()) // (Binary Large Object) es un objeto en JavaScript que representa datos binarios crudos, inmutables. 
        //     .then(blob => { // Puede contener cualquier tipo de dato (texto, imágenes, PDFs, etc.)
        //         // Creando un enlace de descarga
        //         // Blob nos permite descargarlo directamente en el navegador
        //         const url = window.URL.createObjectURL(blob); // Crea una URL temporal que apunta al Blob en memoria
        //         const a = document.createElement('a');
        //         a.href = url;
        //         a.download = "documentacion_modulo.pdf"; //// Nombre del archivo a descargar
        //         document.body.appendChild(a);
        //         a.click(); // Simular click en el enlace
        //         document.body.removeChild(a);
        //         window.URL.revokeObjectURL(url); // Liberar memoria
        //     })
        //     .catch(error => console.error('Error:', error));

        fetch('http://localhost:8080/debug-json', {
            method: 'POST', 
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                [csrfHeaderName]: csrfToken
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(text => console.log("Respuesta:", text))
        .catch(error => console.error('Error:', error));

        //Mensaje de Confirmacion
        alert("Documento Generado");
    }

    // Event listeners
    addApiBtn.addEventListener('click', addApiTab);
    document.querySelectorAll('.api-tab').forEach(tab => {
        tab.addEventListener('click', switchTab);
    });
    form.addEventListener('submit', handleSubmit);

});