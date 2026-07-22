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

    @app.route('/benchmark/fileupload-00/BenchmarkTest01249', methods=['GET', 'POST'])
    def BenchmarkTest01249():
        import os

        uploaded_file = request.files.get("file")
        subdir = request.values.get("folder", "incoming")
        os.makedirs(os.path.join(UPLOAD_DIR, subdir), exist_ok=True)
        destination = os.path.join(UPLOAD_DIR, subdir, uploaded_file.filename)

        uploaded_file.save(destination)

        return make_response(f"stored at: {destination}")

