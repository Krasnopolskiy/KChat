const HtmlWebpackPlugin = require('html-webpack-plugin')
const {resolve} = require("path");
const fs = require("fs")

const pages = fs.readdirSync('./src/pages/')

module.exports = {
    devtool: 'cheap-module-source-map',
    entry: pages.reduce((a, v) => ({...a, [v]: resolve(__dirname, 'src', 'pages', v, `${v}.js`)}), {}),
    output: {
        path: resolve(__dirname, 'build'),
        filename: 'assets/[name].bundle.js'
    },
    module: {
        rules: [
            {test: /\.js$/, exclude: /node_modules/, loader: 'babel-loader'}
        ]
    },
    plugins: pages.map(page =>
        new HtmlWebpackPlugin({
            chunks: [page],
            filename: `${page}.html`,
            template: "./templates/template.html",
            publicPath: "/static/"
        })
    )
}
