const API_URL = "http://localhost:8080/api";

export async function calcularRotaRodoviaria(origem, destino) {
    const url = `${API_URL}/rota/rodoviaria?origem=${encodeURIComponent(origem)}&destino=${encodeURIComponent(destino)}`;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error("Não foi possível calcular a rota rodoviária.");
    }
    return await response.json();    
}

export async function calcularRotaHibridaKruskal(origem, destino) {
    const url = `${API_URL}/rota/hibrida/kruskal?origem=${encodeURIComponent(origem)}&destino=${encodeURIComponent(destino)}`;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error("Não foi possível calcular a rota híbrida com Kruskal.");
    }
    return await response.json();
}

export async function calcularRotaHibridaGenetico(origem, destino) {
    const url = `${API_URL}/rota/hibrida/genetico?origem=${encodeURIComponent(origem)}&destino=${encodeURIComponent(destino)}`;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error("Não foi possível calcular a rota híbrida com Algoritmo Genético.");
    }
    return await response.json();
}

export async function buscarMalhaKruskal() {
    const url = `${API_URL}/ferrovia/kruskal`;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error("Não foi possível gerar a malha de Kruskal.");
    }
    return await response.json();
}

export async function buscarMalhaGenetica() {
    const url = `${API_URL}/ferrovia/genetico`;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error("Não foi possível otimizar a malha com o Algoritmo Genético.");
    }
    return await response.json();
}