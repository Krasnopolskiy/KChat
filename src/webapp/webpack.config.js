const HtmlWebpackPlugin = require('html-webpack-plugin')
const {resolve} = require("path");

const pages = ['index', 'home']

module.exports = {
    devtool: 'cheap-module-source-map',
    entry: pages.reduce((a, v) => ({...a, [v]: resolve(__dirname, 'src', `${v}.js`)}), {}),
    output: {
        path: resolve(__dirname, 'build'),
        filename: '[name].bundle.js'
    },
    module: {
        rules: [
            {test: /\.js$/, exclude: /node_modules/, loader: 'babel-loader'}
        ]
    },
    plugins: pages.map(page =>
        new HtmlWebpackPlugin({
            chunks: ["common", page],
            filename: `${page}.html`,
            template: "./templates/template.html",
        })
    )
}
