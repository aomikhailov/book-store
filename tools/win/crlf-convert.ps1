# /tools/win/crlf-convert.ps1
# --------------------------------------------------------------------
# Скрипт рекурсивно ищет файлы с CRLF (Windows-переводы строк) и
# конвертирует их в LF (Unix-переводы строк).
# --------------------------------------------------------------------
# Версия: 1.3
# Обновлено: 2025-11-02
# --------------------------------------------------------------------

# Переход к папке проекта
Push-Location -Path (Join-Path $PSScriptRoot "..\..")
Write-Host "Начинается конвертация CRLF -> LF в папке: $PWD"

# Список поддерживаемых расширений
$exts = @(".java", ".xml", ".properties", ".env", ".md", ".txt", ".sql", ".gitkeep", ".gitignore", ".gitattributes", ".editorconfig", ".yml", ".yaml", ".json")

# Счётчик обработанных файлов
$count = 0

# Функция для обработки файла
function Convert-ToLF {
    param (
        [string]$file
    )
    try {
        # Чтение содержимого файла
        $content = Get-Content -Path $file -Raw -Encoding UTF8

        # Проверяем наличие CRLF, если есть - заменяем
        if ($content -match "`r`n") {
            $newContent = $content -replace "`r", "" # Убираем CR (`\r`)
            # Пытаемся записать файл обратно
            [System.IO.File]::WriteAllText($file, $newContent, [System.Text.Encoding]::UTF8)
            $script:count++ # Увеличиваем счётчик глобально
            Write-Host "Конвертирован: $file"
        }
    } catch {
        # Обрабатываем ошибку чтения/записи
        Write-Warning "Не удалось обработать файл: $file"
        Write-Warning $_.Exception.Message
    }
}

# Обходим файлы рекурсивно
Get-ChildItem -Recurse -File | ForEach-Object {
    $file = $_.FullName
    $ext = $_.Extension.ToLower()

    # Проверяем, подходит ли файл по расширению
    if ($exts -contains $ext) {
        Convert-ToLF -file $file
    }
}

# Вывод результата
Write-Host "Конвертация завершена. Конвертировано файлов: $count"

# Возврат в исходную рабочую папку
Pop-Location