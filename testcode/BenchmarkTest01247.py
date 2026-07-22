'''
OWASP Benchmark for Python v0.1

This file is part of the Open Web Application Security Project (OWASP) Benchmark Project.
For details, please see https://owasp.org/www-project-benchmark.

The OWASP Benchmark is free software: you can redistribute it and/or modify it under the terms
of the GNU General Public License as published by the Free Software Foundation, version 3.

The OWASP Benchmark is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE. See the GNU General Public License for more details.

  Author: Theo Cartsonis
  Created: 2025
'''

from flask import request, make_response

UPLOAD_DIR = "/tmp/benchmark-uploads"


def init(app):

    @app.route('/benchmark/fileupload-00/BenchmarkTest01247', methods=['GET', 'POST'])
    def BenchmarkTest01247():
        import os

        uploaded_file = request.files.get("file")
        filename = uploaded_file.filename
        os.makedirs(UPLOAD_DIR, exist_ok=True)

        # and double-extension names.
        blocked_extensions = [".php", ".jsp", ".exe"]
        if not any(filename.endswith(extension) for extension in blocked_extensions):
            uploaded_file.save(os.path.join(UPLOAD_DIR, filename))
            return make_response("accepted")

        return make_response("blocked")

