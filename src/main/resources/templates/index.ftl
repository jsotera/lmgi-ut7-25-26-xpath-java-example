<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pronóstico por Horas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background-color: #f0f2f5;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .weather-card {
            border: none;
            border-radius: 15px;
            transition: transform 0.3s ease;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .weather-card:hover {
            transform: translateY(-5px);
        }
        .temp-display {
            font-size: 2rem;
            font-weight: bold;
            color: #333;
        }
        .weather-icon {
            font-size: 3rem;
            margin-bottom: 10px;
        }
        .status-text {
            text-transform: capitalize;
            font-weight: 500;
            color: #666;
        }
        .humidity {
            color: #0d6efd;
            font-weight: 500;
        }
    </style>
</head>
<body>

<div class="container py-5">
    <#list tarjetasPorDia as dia, tarjetas>
        <h2 class="text-center mb-5">${dia}</h2>
        <div class="row g-4">
            <#list tarjetas as tarjeta>
                ${tarjeta}
            </#list>
        </div>
    </#list>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>